package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.RentingInterval;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import com.uns.ftn.rentingservice.domain.RequestStatus;
import com.uns.ftn.rentingservice.dto.CreateResponseDTO;
import com.uns.ftn.rentingservice.dto.GetRentingRequestDTO;
import com.uns.ftn.rentingservice.dto.RentingRequestDTO;
import com.uns.ftn.rentingservice.dto.RequestStatusDTO;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.RentingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class RentingRequestService {

    private AdvertisementRepository adRepo;
    private RentingRequestRepository requestRepo;
    private TaskScheduler taskScheduler;

    @Autowired
    public RentingRequestService(AdvertisementRepository adRepo, RentingRequestRepository requestRepo,
                                 TaskScheduler taskScheduler) {
        this.adRepo = adRepo;
        this.requestRepo = requestRepo;
        this.taskScheduler = taskScheduler;
    }

    public RentingRequest findOne(Long id) { return requestRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("Requested renting request doesn't exist.")); }

    public RentingRequest save(RentingRequest rentingRequest) { return requestRepo.save(rentingRequest); }

    public ResponseEntity<?> createRequest(RentingRequestDTO rdto) {

        checkDateValidityForRequest(rdto);

        // check if all requested advertisements exist
        Set<Advertisement> ads = checkIfAdsExist(rdto.getAdvertisementIDs());

        // check availability of all requested advertisements for suggested renting interval
        for (Advertisement ad : ads) {
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
                    if (request.getStatus() == RequestStatus.paid && currentTime.after(request.getEndDate())) {
                        GetRentingRequestDTO gdto = new GetRentingRequestDTO(request);
                        gdto.setAdvertisementID(ad.getId());
                        retval.add(gdto);
                    }
                }
            }
        }

        return retval;
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

}
