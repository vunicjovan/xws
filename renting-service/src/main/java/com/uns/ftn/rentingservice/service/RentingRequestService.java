package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.RentingInterval;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import com.uns.ftn.rentingservice.domain.RequestStatus;
import com.uns.ftn.rentingservice.dto.CreateResponseDTO;
import com.uns.ftn.rentingservice.dto.RentingRequestDTO;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.RentingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class RentingRequestService {

    private AdvertisementRepository adRepo;
    private RentingRequestRepository requestRepo;

    @Autowired
    public RentingRequestService(AdvertisementRepository adRepo, RentingRequestRepository requestRepo) {
        this.adRepo = adRepo;
        this.requestRepo = requestRepo;
    }

    public ResponseEntity<?> createRequest(RentingRequestDTO rdto) {

        if (rdto.getStartDate().after(rdto.getEndDate())) {
            throw new BadRequestException("Start of renting interval cannot be after the end of it.");
        }

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

        return new ResponseEntity<>(new CreateResponseDTO(req), HttpStatus.CREATED);
    }

    private Set<Advertisement> checkIfAdsExist(Set<Long> adIDs) {
        Set<Advertisement> adSet = new HashSet<>();
        for (Long id: adIDs) {
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
                if (!(endDate.before(rentingInterval.getStartDate()) || startDate.after(rentingInterval.getEndDate()))){
                    overlaps = true;
                    break;
                }
            }
        }

        return overlaps;
    }

}
