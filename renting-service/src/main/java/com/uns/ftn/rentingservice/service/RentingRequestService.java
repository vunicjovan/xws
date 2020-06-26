package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.client.AdvertisementClient;
import com.uns.ftn.rentingservice.client.CommentClient;
import com.uns.ftn.rentingservice.client.MessageClient;
import com.uns.ftn.rentingservice.domain.*;
import com.uns.ftn.rentingservice.dto.*;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.RentingReportRepository;
import com.uns.ftn.rentingservice.repository.RentingRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RentingRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentingRequestService.class);

    private AdvertisementRepository adRepo;
    private RentingRequestRepository requestRepo;
    private TaskScheduler taskScheduler;
    private AdvertisementClient advertisementClient;
    private CommentService commentService;
    private CommentClient commentClient;
    private final RentingReportRepository rentingReportRepository;
    private MessageClient messageClient;


    @Autowired
    public RentingRequestService(AdvertisementRepository adRepo, RentingRequestRepository requestRepo,
                                 TaskScheduler taskScheduler, AdvertisementClient advertisementClient,
                                 CommentService commentService, CommentClient commentClient,
                                 RentingReportRepository rentingReportRepository,
                                 MessageClient messageClient) {
        this.adRepo = adRepo;
        this.requestRepo = requestRepo;
        this.taskScheduler = taskScheduler;
        this.advertisementClient = advertisementClient;
        this.commentService = commentService;
        this.commentClient = commentClient;
        this.rentingReportRepository = rentingReportRepository;
        this.messageClient = messageClient;
    }

    public RentingRequest findOne(Long id) { return requestRepo.findById(id)
            .orElseThrow(() -> {
                LOGGER.warn("Database query: request[id={}] doesn't exist", id);
                return new NotFoundException("Requested renting request doesn't exist.");
            }); }

    public RentingRequest save(RentingRequest rentingRequest) { return requestRepo.save(rentingRequest); }

    public ResponseEntity<?> createRequest(RentingRequestDTO rdto) {
        LOGGER.debug("Adding new rentingRequest[senderId={}]", rdto.getSenderId());
        checkDateValidityForRequest(rdto);

        // check if all requested advertisements exist
        Set<Advertisement> ads = checkIfAdsExist(rdto.getAdvertisementIDs());

        // check availability of all requested advertisements for suggested renting interval
        for (Advertisement ad : ads) {
            if (findIfRangeOverlaps(ad.getRentingIntervals(), rdto.getStartDate(), rdto.getEndDate())) {
                LOGGER.warn("Reverting adding new rentingRequest[senderId={}]: at least one vehicle isn't available",
                        rdto.getSenderId());
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

        req = requestRepo.save(req);
        LOGGER.info("Database entry: created new rentingRequest[id={}, senderId={}]", req.getId(), req.getSenderId());

        RentingRequest finalReq = req;
        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                LOGGER.debug("Checking if rentingRequest[id={}] is handled", finalReq.getId());
                RentingRequest rentingRequest = findOne(finalReq.getId());
                if (rentingRequest.getStatus() == RequestStatus.pending) {
                    rentingRequest.setStatus(RequestStatus.canceled);
                    save(rentingRequest);
                    LOGGER.info("Database update: rentingRequest[id={}] automatically rejected after 24h",
                            rentingRequest.getId());
                }
            }
        };

        taskScheduler.schedule(myRunnable, new Date(System.currentTimeMillis() + 86400000));

        LOGGER.debug("Finished adding new rentingRequest[senderId={}]", finalReq.getSenderId());
        return new ResponseEntity<>(new CreateResponseDTO(req), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateRequestStatus(Long id, RequestStatusDTO request) {
        LOGGER.debug("Updating rentingRequest[id={}, status={}]", id, request.getStatus());
        RentingRequest rentingRequest = findOne(id);

        rentingRequest.setStatus(request.getStatus());
        rentingRequest = save(rentingRequest);

        if(request.getStatus() == RequestStatus.paid) {
            RentingRequest finalRentingRequest = rentingRequest;

            try {
                LOGGER.debug("Feign client requested creating new chat in message-service");
                messageClient.createChat(finalRentingRequest.getAdvertisements().iterator().next().getOwnerId(),
                        rentingRequest.getSenderId());
            } catch (Exception e) {
                LOGGER.error("Error occurred during contacting message-service feign client", e);
            }

            finalRentingRequest.getAdvertisements().forEach(advertisement -> {
                advertisement.getRentingRequests().forEach(req -> {
                    if(req.getId() != finalRentingRequest.getId() && req.getStatus() == RequestStatus.pending ) {
                        if(!(req.getEndDate().before(finalRentingRequest.getStartDate()) ||
                                req.getStartDate().after(finalRentingRequest.getEndDate()))) {
                            req.setStatus(RequestStatus.canceled);
                            save(req);
                            LOGGER.info("Database update: rentingRequest[id={}] automatically rejected because " +
                                    "overlapping request was reserved", req.getId());
                        }
                    }
                });
            });
        }

        LOGGER.debug("Finished updating rentingRequest[id={}, status={}]", id, request.getStatus());
        return new ResponseEntity<>(
                new RequestStatusDTO(rentingRequest.getId(), rentingRequest.getStatus()), HttpStatus.OK);
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

    public Set<ReqResponseDTO> getRequestForUser(Long id) {
        List<RentingRequest> requests = requestRepo.findAllBySenderId(id);
        Set<RentingRequest> response = new HashSet<>();

        if(requests.isEmpty()) {
            Set<Advertisement> advertisements = adRepo.findAllByOwnerId(id);
            for(Advertisement ad : advertisements) {
                for(RentingRequest req : ad.getRentingRequests()) {
                    if(req.getStatus() == RequestStatus.pending &&
                            req.getEndDate().after(new Date(System.currentTimeMillis()))){
                        response.add(req);
                    }
                }
            }

            return getReqResponseDTOS(response);
        }

        for(RentingRequest req : requests) {
            if(req.getStatus() == RequestStatus.pending &&
                    req.getEndDate().after(new Date(System.currentTimeMillis()))) {
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
                    availableCommentDTO.setRentingInterval(new RentingIntervalDTO(request.getStartDate(),
                            request.getEndDate()));
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

       /* advertisements.forEach(advertisement -> {
            AvailableCommentDTO availableCommentDTO = new AvailableCommentDTO();
            availableCommentDTO.setAdvertisement(advertisementClient.getAd(advertisement.getId()));
            availableCommentDTO.setCommentAvailable(false);
            availableCommentDTO.setRentingRequestId(null);
            advertisement.getRentingRequests().forEach(request -> {
                if(request.getSenderId().equals(id) && checkIfRequestIsFinished(request)) {
                    availableCommentDTO.getRentingIntervals().add(new RentingIntervalDTO(request.getStartDate(),
                            request.getEndDate()));
                    Comment comment = commentService.findIfExist(advertisement, request);
                    if(comment == null) {
                        availableCommentDTO.setCommentAvailable(true);
                        availableCommentDTO.setRentingRequestId(request.getId());
                    } else {
                        availableCommentDTO.getComments().add(commentClient.getComment(comment.getId()));
                    }
                }
            });
            response.add(availableCommentDTO);
        });*/

        return response;
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
            Advertisement ad = this.adRepo.findById(id).orElseThrow(() -> {
                LOGGER.warn("Database query: advertisement[id={}] doesn't exist", id);
                return new NotFoundException("Requested advertisement does not exist.");
            });
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
            LOGGER.warn("Invalid rentingRequest[senderId={}] missing start or end date", rdto.getSenderId());
            throw new BadRequestException("Every request must have both begin and end dates for renting.");
        }

        if (rdto.getStartDate().after(rdto.getEndDate())) {
            LOGGER.warn("Invalid rentingRequest[senderId={}] end date is before start date", rdto.getSenderId());
            throw new BadRequestException("Start of renting interval cannot be after the end of it.");
        }
    }

}
