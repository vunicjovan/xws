package com.uns.ftn.viewservice.service;

import com.uns.ftn.viewservice.client.AccountClient;
import com.uns.ftn.viewservice.domain.*;
import com.uns.ftn.viewservice.dto.DetailedAdvertisementDTO;
import com.uns.ftn.viewservice.dto.SimpleAdvertisementDTO;
import com.uns.ftn.viewservice.exceptions.NotFoundException;
import com.uns.ftn.viewservice.repository.AdvertisementRepository;
import com.uns.ftn.viewservice.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViewService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AccountClient accountClient;

    public Set<SimpleAdvertisementDTO> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementRepository.findAll();
        Set<SimpleAdvertisementDTO> simpleAdvertisementDTOSet = new HashSet<>();

        advertisements.forEach(advertisement -> simpleAdvertisementDTOSet.add(new SimpleAdvertisementDTO(
                advertisement.getId(),
                advertisement.getPrice(),
                advertisement.getPlace(),
                advertisement.getVehicle().getModel().getBrand().getName(),
                advertisement.getVehicle().getModel().getName(),
                advertisement.getPhotos()
                        .stream().map(photo -> photo.getPath()).collect(Collectors.toSet())
        )));

        return simpleAdvertisementDTOSet;
    }

    public DetailedAdvertisementDTO getAdvertisement(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advertisement with that id does not exist!"));

        String owner;

        try {
            owner = accountClient.getOwnerName(advertisement.getOwnerId());
        } catch (Exception e) {
            throw new NotFoundException("Advertisement owner does not exist!");
        }

        DetailedAdvertisementDTO detailedAdvertisementDTO = new DetailedAdvertisementDTO(
                advertisement.getId(),
                advertisement.getVehicle().getModel().getBrand().getName(),
                advertisement.getVehicle().getModel().getName(),
                advertisement.getVehicle().getVehicleClass().getName(),
                advertisement.getVehicle().getGearboxType().getName(),
                advertisement.getVehicle().getFuelType().getName(),
                owner,
                advertisement.getPlace(),
                advertisement.getPrice(),
                advertisement.getVehicle().getKilometersTraveled(),
                advertisement.getCollisionDamageWaiver(),
                advertisement.getKilometersPerDayLimit(),
                advertisement.getVehicle().getChildSeatNumber(),
                advertisement.getVehicle().getHasAndroid(),
                advertisement.getDescription(),
                null
                //advertisement.getPhotos().stream().map(photo -> photo.getPath()).collect(Collectors.toSet())
        );

        return detailedAdvertisementDTO;
    }

}
