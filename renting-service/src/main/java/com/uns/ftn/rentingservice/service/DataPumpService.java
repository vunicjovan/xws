package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.dto.AdvertisementDTO;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPumpService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public void advertisementHandler(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = findAdvertisementById(advertisementDTO.getId());

        if (advertisement == null) {
            advertisement = new Advertisement();
            advertisement.setId(advertisementDTO.getId());
        }

        advertisement.setOwnerId(advertisementDTO.getOwnerId());

        advertisementRepository.save(advertisement);
    }

    private Advertisement findAdvertisementById(Long id) { return advertisementRepository.findById(id).orElse(null); }

}
