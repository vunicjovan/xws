package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.RentingInterval;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import com.uns.ftn.rentingservice.domain.RequestStatus;
import com.uns.ftn.rentingservice.dto.AdvertisementDTO;
import com.uns.ftn.rentingservice.dto.RentingIntervalDTO;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.RentingIntervalRepository;
import com.uns.ftn.rentingservice.repository.RentingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class DataPumpService {

    private final AdvertisementRepository advertisementRepository;

    private final RentingIntervalRepository rentingIntervalRepository;

    private final RentingRequestRepository rentingRequestRepository;

    public DataPumpService(AdvertisementRepository advertisementRepository,
                           RentingIntervalRepository rentingIntervalRepository, RentingRequestRepository rentingRequestRepository) {
        this.advertisementRepository = advertisementRepository;
        this.rentingIntervalRepository = rentingIntervalRepository;
        this.rentingRequestRepository = rentingRequestRepository;
    }

    public void advertisementHandler(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = findAdvertisementById(advertisementDTO.getId());

        if (advertisement == null) {
            advertisement = new Advertisement();
            advertisement.setId(advertisementDTO.getId());
        }

        advertisement.setOwnerId(advertisementDTO.getOwnerId());

        advertisementRepository.save(advertisement);
    }

    public void rentingIntervalHandler(RentingIntervalDTO rentingIntervalDTO) {
        RentingInterval rentingInterval = findRentingIntervalById(rentingIntervalDTO.getId());

        if (rentingInterval == null) {
            rentingInterval = new RentingInterval();
            rentingInterval.setId(rentingIntervalDTO.getId());
            rentingInterval.setAdvertisement(findAdvertisementById(rentingIntervalDTO.getAdvertisementId()));
            rentingInterval.setStartDate(rentingIntervalDTO.getStartDate());
            rentingInterval.setEndDate(rentingIntervalDTO.getEndDate());
        }

        Advertisement advertisement = findAdvertisementById(rentingInterval.getAdvertisement().getId());

        for (RentingRequest r : advertisement.getRentingRequests()) {
            if (!(rentingInterval.getEndDate().before(r.getStartDate()) ||
                    rentingInterval.getStartDate().after(r.getEndDate()))) {
                RentingRequest rentingRequest = findRentingRequestById(r.getId());

                if(rentingRequest.getStatus() != RequestStatus.canceled &&
                        rentingRequest.getStartDate().after(new Date(System.currentTimeMillis()))) {
                    rentingRequest.setStatus(RequestStatus.canceled);

                    rentingRequestRepository.save(rentingRequest);
                }
            }
        }

        rentingIntervalRepository.save(rentingInterval);
    }

    private Advertisement findAdvertisementById(Long id) {
        return advertisementRepository.findById(id).orElse(null);
    }
    private RentingInterval findRentingIntervalById(Long id) {
        return rentingIntervalRepository.findById(id).orElse(null);
    }
    private RentingRequest findRentingRequestById(Long id) {
        return rentingRequestRepository.findById(id).orElse(null);
    }
}
