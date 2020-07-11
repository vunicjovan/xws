package com.uns.ftn.rentingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.rentingservice.client.AccountClient;
import com.uns.ftn.rentingservice.client.AdvertisementClient;
import com.uns.ftn.rentingservice.client.CommentClient;
import com.uns.ftn.rentingservice.client.MessageClient;
import com.uns.ftn.rentingservice.components.QueueProducer;
import com.uns.ftn.rentingservice.domain.*;
import com.uns.ftn.rentingservice.dto.*;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.RentingReportRepository;
import com.uns.ftn.rentingservice.repository.RentingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentingRequestService {

    private AdvertisementRepository adRepo;
    private RentingRequestRepository requestRepo;
    private TaskScheduler taskScheduler;
    private AdvertisementClient advertisementClient;
    private CommentService commentService;
    private CommentClient commentClient;
    private final RentingReportRepository rentingReportRepository;
    private MessageClient messageClient;
    private QueueProducer queueProducer;
    private AccountClient accountClient;
    private DebtService debtService;

    @Autowired
    public RentingRequestService(AdvertisementRepository adRepo, RentingRequestRepository requestRepo,
                                 TaskScheduler taskScheduler, AdvertisementClient advertisementClient,
                                 CommentService commentService, CommentClient commentClient,
                                 RentingReportRepository rentingReportRepository,
                                 MessageClient messageClient, QueueProducer queueProducer,
                                 AccountClient accountClient, DebtService debtService) {
        this.adRepo = adRepo;
        this.requestRepo = requestRepo;
        this.taskScheduler = taskScheduler;
        this.advertisementClient = advertisementClient;
        this.commentService = commentService;
        this.commentClient = commentClient;
        this.rentingReportRepository = rentingReportRepository;
        this.messageClient = messageClient;
        this.queueProducer = queueProducer;
        this.accountClient = accountClient;
        this.debtService = debtService;
    }

    public RentingRequest findOne(Long id) { return requestRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("Requested renting request doesn't exist.")); }

    public RentingRequest save(RentingRequest rentingRequest) { return requestRepo.save(rentingRequest); }

    public ResponseEntity<?> createRequest(RentingRequestDTO rdto) {
        if (!debtService.getDebt(rdto.getSenderId()).isEmpty()) {
            throw new BadRequestException("You have to pay all your debts in order to rent a new vehicle.");
        }

        checkDateValidityForRequest(rdto);

        // check if all requested advertisements exist
        Set<Advertisement> ads = checkIfAdsExist(rdto.getAdvertisementIDs());

        // check availability of all requested advertisements for suggested renting interval
        for (Advertisement ad : ads) {
            if (ad.getDeleted()) {
                throw new BadRequestException("Selected ad does not exist!");
            }
            if (findIfRangeOverlaps(ad.getRentingIntervals(), rdto.getStartDate(), rdto.getEndDate())) {
                throw new BadRequestException("At least one vehicle is not available at the requested time.");
            }
        }

        // create renting request
        RentingRequest req = new RentingRequest();
        req.setStartDate(rdto.getStartDate());
        req.setEndDate(rdto.getEndDate());
        req.setStatus(RequestStatus.pending);
        req.setSenderId(rdto.getSenderId());

        req.setAdvertisements(ads);

        req = this.requestRepo.save(req);

        MessageDTO mdto = new MessageDTO("RentaSoul Platform",
                "You have a new renting request.", true);
        try {
            queueProducer.produce(mdto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RentingRequest finalReq = req;
        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                RentingRequest rentingRequest = findOne(finalReq.getId());
                if (rentingRequest.getStatus() == RequestStatus.pending) {
                    rentingRequest.setStatus(RequestStatus.canceled);
                    save(rentingRequest);
                }
            }
        };

        taskScheduler.schedule(myRunnable, new Date(System.currentTimeMillis() + 86400000));

        return new ResponseEntity<>(new CreateResponseDTO(req), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateRequestStatus(Long id, RequestStatusDTO request) {
        RentingRequest rentingRequest = findOne(id);

        rentingRequest.setStatus(request.getStatus());
        rentingRequest = save(rentingRequest);

        if(request.getStatus() == RequestStatus.paid) {
            RentingRequest finalRentingRequest = rentingRequest;

            MessageDTO mdto = new MessageDTO("RentaSoul Platform",
                    "Your renting request has been accepted.", true);
            try {
                queueProducer.produce(mdto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            try {
                messageClient.createChat(finalRentingRequest.getAdvertisements().iterator().next().getOwnerId(),
                        rentingRequest.getSenderId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            finalRentingRequest.getAdvertisements().forEach(advertisement -> {
                RentingInterval rentingInterval = new RentingInterval();
                rentingInterval.setStartDate(finalRentingRequest.getStartDate());
                rentingInterval.setEndDate(finalRentingRequest.getEndDate());
                rentingInterval.setAdvertisement(advertisement);

                try {
                    queueProducer.produceInterval(new RentingIntervalDTO(rentingInterval));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                advertisement.getRentingRequests().forEach(req -> {
                    if (req.getId() != finalRentingRequest.getId() && req.getStatus() == RequestStatus.pending) {
                        if (!(req.getEndDate().before(finalRentingRequest.getStartDate()) ||
                                req.getStartDate().after(finalRentingRequest.getEndDate()))) {
                            req.setStatus(RequestStatus.canceled);
                            save(req);

                            MessageDTO message = new MessageDTO("RentaSoul Platform",
                                    "Your renting request has been rejected.", true);
                            try {
                                queueProducer.produce(message);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            });
        } else if (request.getStatus() == RequestStatus.canceled) {
            MessageDTO mdto = new MessageDTO("RentaSoul Platform",
                    "Your renting request has been rejected.", true);
            try {
                queueProducer.produce(mdto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(
                new RequestStatusDTO(rentingRequest.getId(), rentingRequest.getStatus()), HttpStatus.OK);
    }

    public ResponseEntity<?> cancelRequest(Long id) {
        RentingRequest request = findOne(id);
        if (request.getStatus() == RequestStatus.pending || (request.getStatus() == RequestStatus.paid &&
                getTomorrowDate().before(request.getStartDate()))) {
            request.setStatus(RequestStatus.canceled);
            request = save(request);

            //agent notification placeholder: notify agent through email about cancelation
            MessageDTO mdto = new MessageDTO("Renting request canceled", "Request with ID " + request.getId() + " for your vehicle(s) was canceled.", true);
            try {
                queueProducer.produce(mdto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            //feign client placeholder: increment numberOfCancelations for request sender
            System.out.println(accountClient.incrementCancelation(request.getSenderId()));

            return new ResponseEntity<>(new RentingRequestDTO(request), HttpStatus.OK);
        } else {
            throw new BadRequestException("Cancelation period has expired. Request cannot be canceled.");
        }
    }

    private Date getTomorrowDate() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();

        return dt;
    }

    public Set<GetRentingRequestDTO> getAllFinished(Long agentId) {
        Set<Advertisement> agentsAds = adRepo.findAllByOwnerId(agentId);
        Set<GetRentingRequestDTO> retval = new HashSet<>();
        Date currentTime = new Date();

        if (!agentsAds.isEmpty()) {
            for (Advertisement ad : agentsAds) {
                for (RentingRequest request : ad.getRentingRequests()) {
                    RentingReport report = rentingReportRepository.findByAdvertisementAndRentingRequest(ad, request);
                    if (report == null && request.getStatus() == RequestStatus.paid &&
                            currentTime.after(request.getEndDate())) {
                        GetRentingRequestDTO gdto = new GetRentingRequestDTO(request);
                        gdto.setAdvertisementID(ad.getId());
                        retval.add(gdto);
                    }
                }
            }
        }

        return retval;
    }

    public Set<FinishedRequestDTO> getResponseFinished(Long id) {
        Set<GetRentingRequestDTO> finished = getAllFinished(id);
        Set<FinishedRequestDTO> retval = finished.stream().map((req) -> {
            FinishedRequestDTO finishedRequestDTO = new FinishedRequestDTO();
            finishedRequestDTO.setRequestId(req.getRequestId());
            finishedRequestDTO.setAdvertisementID(req.getAdvertisementID());
            finishedRequestDTO.setStartDate(formatToString(req.getStartDate()));
            finishedRequestDTO.setEndDate(formatToString(req.getEndDate()));
            AdvertClientResponseDTO ad = advertisementClient.getAd(req.getAdvertisementID());
            finishedRequestDTO.setVehicle(ad.getBrand() + " " + ad.getModel() );
            return finishedRequestDTO;
        }).collect(Collectors.toSet());

        return retval;
    }

    public Set<ReqResponseDTO> getRequestForUser(Long id) {
        List<RentingRequest> requests = requestRepo.findAllBySenderId(id);
        Set<RentingRequest> response = new HashSet<>();

        if(requests.isEmpty()) {
            Set<Advertisement> advertisements = adRepo.findAllByOwnerId(id);
            for(Advertisement ad : advertisements) {
                for(RentingRequest req : ad.getRentingRequests()) {
                    if(req.getStatus() == RequestStatus.pending &&
                            req.getStartDate().after(new Date(System.currentTimeMillis()))){
                        response.add(req);
                    }
                }
            }

            return getReqResponseDTOS(response);
        }

        for(RentingRequest req : requests) {
            if(req.getStatus() != RequestStatus.canceled &&
                    req.getStartDate().after(new Date(System.currentTimeMillis() + 86400000))) {
                response.add(req);
            }
        }

        return getReqResponseDTOS(response);
    }

    public Set<AvailableCommentDTO> getHistoryUser(Long id) {
        List<RentingRequest> requests = requestRepo.findAllBySenderId(id);
        Set<Advertisement> advertisements = new HashSet<>();
        Set<AvailableCommentDTO> response = new HashSet<>();

        for(RentingRequest request : requests) {
            if(checkIfRequestIsFinished(request)) {
                for(Advertisement advertisement: request.getAdvertisements()) {
                    AvailableCommentDTO availableCommentDTO = new AvailableCommentDTO();
                    availableCommentDTO.setAdvertisement(advertisementClient.getAd(advertisement.getId()));
                    availableCommentDTO.setRentingRequestId(request.getId());
                    availableCommentDTO.setRentingInterval(new RentingIntervalDTO(null, request.getStartDate(),
                            request.getEndDate(), null));
                    Comment comment = commentService.findIfExist(advertisement, request);
                    if(comment == null) {
                        availableCommentDTO.setCommentAvailable(true);
                    } else {
                        availableCommentDTO.setComment(commentClient.getComment(comment.getId()));
                    }
                    availableCommentDTO.setRatingAvailable(true);
                    availableCommentDTO.getAdvertisement().getRatedByUsers().forEach(ratedUser -> {
                        if (ratedUser.getUserId().equals(id)) {
                            availableCommentDTO.setRatingAvailable(false);
                        }
                    });

                    response.add(availableCommentDTO);
                }
            }
        }

        return response;
    }

    public ReqResponseDTO getRequestById(Long id) {
            RentingRequest request = findOne(id);

            ReqResponseDTO responseDTO = new ReqResponseDTO();
            responseDTO.setId(request.getId());
            responseDTO.setStartDate(request.getStartDate());
            responseDTO.setEndDate(request.getEndDate());
            responseDTO.setStartDate(request.getStartDate());
            responseDTO.setAdvertisements(request.getAdvertisements().stream().map(advertisement -> {
                AdvertClientResponseDTO ad = advertisementClient.getAd(advertisement.getId());
                return ad;
            }).collect(Collectors.toSet()));

            return responseDTO;
    }

    private boolean checkIfRequestIsFinished(RentingRequest rentingRequest)  {

        if(rentingRequest.getEndDate().before(new Date(System.currentTimeMillis())) &&
                rentingRequest.getStatus().equals(RequestStatus.paid)) {
            return true;
        }

        return  false;
    }

    private Set<ReqResponseDTO> getReqResponseDTOS(Set<RentingRequest> response) {
        return response.stream().map(req -> {
            ReqResponseDTO resp = new ReqResponseDTO();
            resp.setId(req.getId());
            resp.setSenderId(req.getSenderId());
            resp.setStartDate(req.getStartDate());
            resp.setEndDate(req.getEndDate());
            for(Advertisement adv : req.getAdvertisements()) {
                AdvertClientResponseDTO ad = advertisementClient.getAd(adv.getId());
                resp.getAdvertisements().add(ad);
            }
            return resp;
        }).collect(Collectors.toSet());
    }

    private Set<Advertisement> checkIfAdsExist(Set<Long> adIDs) {
        Set<Advertisement> adSet = new HashSet<>();
        for (Long id : adIDs) {
            Advertisement ad = this.adRepo.findById(id).orElseThrow(() ->
                    new NotFoundException("Requested advertisement does not exist."));
            adSet.add(ad);
        }

        return adSet;
    }

    private Boolean findIfRangeOverlaps(Set<RentingInterval> rentingIntervals, Date startDate, Date endDate) {
        Boolean overlaps = false;

        if (!rentingIntervals.isEmpty()) {
            for(RentingInterval rentingInterval : rentingIntervals) {
                if (!(endDate.before(rentingInterval.getStartDate()) || startDate.after(rentingInterval.getEndDate()))) {
                    overlaps = true;
                    break;
                }
            }
        }

        return overlaps;
    }

    private void checkDateValidityForRequest(RentingRequestDTO rdto) {
        if (rdto.getStartDate() == null || rdto.getEndDate() == null) {
            throw new BadRequestException("Every request must have both begin and end dates for renting.");
        }

        if (rdto.getStartDate().after(rdto.getEndDate())) {
            throw new BadRequestException("Start of renting interval cannot be after the end of it.");
        }
    }

    public String formatToString(Date date) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String formattedDate = formatter.format(date);

        return formattedDate;
    }

}
