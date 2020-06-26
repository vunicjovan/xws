package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.dto.AdvertisementDTO;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPumpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataPumpService.class);

    private AdvertisementRepository advertisementRepository;

    @Autowired
    public DataPumpService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public void advertisementHandler(AdvertisementDTO advertisementDTO) {
        LOGGER.debug("Advertisement data pump handler start advertisement[id={}]", advertisementDTO.getId());
        Advertisement advertisement = findAdvertisementById(advertisementDTO.getId());

        if (advertisement == null) {
            advertisement = new Advertisement();
            advertisement.setId(advertisementDTO.getId());
        }

        advertisement.setOwnerId(advertisementDTO.getOwnerId());

        advertisementRepository.save(advertisement);
        LOGGER.info("Database entry: saved advertisement[id={}]", advertisement.getId());
        LOGGER.debug("Advertisement data pump handler finish advertisement[id={}]", advertisement.getId());
    }

    private Advertisement findAdvertisementById(Long id) { return advertisementRepository.findById(id).orElse(null); }

}
