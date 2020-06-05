package com.uns.ftn.agentservice.service;

import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.RentingInterval;
import com.uns.ftn.agentservice.dto.RentingIntervalDTO;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.repository.AdvertisementRepository;
import com.uns.ftn.agentservice.repository.RentingIntervalRepository;
import javassist.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RentingIntervalService {

    private final RentingIntervalRepository rentingIntervalRepository;

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public RentingIntervalService(RentingIntervalRepository rentingIntervalRepository, AdvertisementRepository advertisementRepository) {
        this.rentingIntervalRepository = rentingIntervalRepository;
        this.advertisementRepository = advertisementRepository;
    }

    public RentingInterval save(RentingInterval rentingInterval) {
        return rentingIntervalRepository.save(rentingInterval);
    }

    public Set<RentingInterval> getAll() {
        return rentingIntervalRepository.findAll().stream().collect(Collectors.toSet());
    }

    public ResponseEntity<?> manuallyAddInterval(RentingIntervalDTO rentingIntervalDTO) {
        Advertisement advertisement = advertisementRepository.findById(rentingIntervalDTO.getAdvertisementId()).
                    orElse(null);

        if (advertisement == null) {
            throw new BadRequestException("Renting interval does not exist.");
        }

        if (rentingIntervalDTO.getStartDate().after(rentingIntervalDTO.getEndDate())) {
            throw new BadRequestException("Starting date cannot be after ending date.");
        }

        RentingInterval rentingInterval = new RentingInterval();
        rentingInterval.setAdvertisement(advertisement);
        rentingInterval.setStartDate(rentingIntervalDTO.getStartDate());
        rentingInterval.setEndDate(rentingIntervalDTO.getEndDate());

        if (!findIfRangeOverlaps(getAll(), rentingIntervalDTO.getStartDate(), rentingIntervalDTO.getEndDate())) {
            save(rentingInterval);
            return new ResponseEntity<> (new RentingIntervalDTO(rentingInterval), HttpStatus.CREATED);
        } else {
            throw new BadRequestException("It is not possible to fit in desired renting interval. Please choose another.");
        }

    }




    private Boolean findIfRangeOverlaps(Set<RentingInterval> rentingIntervals, Date startDate, Date endDate) {
        Boolean overlaps = false;
        for(RentingInterval rentingInterval : rentingIntervals) {
            if (!(endDate.before(rentingInterval.getStartDate()) || startDate.after(rentingInterval.getEndDate()))){
                overlaps = true;
                break;
            }
        }
        return overlaps;
    }

}
