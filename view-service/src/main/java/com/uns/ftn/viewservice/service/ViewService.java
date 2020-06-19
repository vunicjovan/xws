package com.uns.ftn.viewservice.service;

import com.uns.ftn.viewservice.client.AccountClient;
import com.uns.ftn.viewservice.domain.*;
import com.uns.ftn.viewservice.dto.AdvertClientResponseDTO;
import com.uns.ftn.viewservice.dto.CartAdvertisementDTO;
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
                advertisement.getLocation(),
                advertisement.getVehicle().getModel().getBrand().getName(),
                advertisement.getVehicle().getModel().getName(),
                advertisement.getPhotos().stream().map(photo -> photo.getPath()).collect(Collectors.toSet())
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
            e.printStackTrace();
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
                advertisement.getLocation(),
                advertisement.getPrice(),
                advertisement.getVehicle().getKilometersTraveled(),
                advertisement.getCollisionDamageWaiver(),
                advertisement.getKilometersPerDayLimit(),
                advertisement.getVehicle().getChildSeatNumber(),
                advertisement.getVehicle().getHasAndroid(),
                advertisement.getDescription(),
                advertisement.getPhotos().stream().map(photo -> photo.getPath()).collect(Collectors.toSet()),
                advertisement.getOwnerId()
        );

        return detailedAdvertisementDTO;
    }

    public AdvertClientResponseDTO getAdvert(Long id) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested advertisement doesn't exist."));

        return new AdvertClientResponseDTO(ad.getId(), ad.getVehicle().getModel().getName(),
                ad.getVehicle().getModel().getBrand().getName(), ad.getLocation(), ad.getPrice());
    }

    public Set<SimpleAdvertisementDTO> getAgentsAdvertisements(Long id) {
        return advertisementRepository.findAllByOwnerId(id).stream()
                .map(SimpleAdvertisementDTO::new).collect(Collectors.toSet());
    }



    public Set<CartAdvertisementDTO> getCartAdvertisements(List<Long> advertisementIdList) {
        List<Advertisement> advertisements = advertisementRepository.findAllById(advertisementIdList);

        return advertisements.stream().
                map(advertisement -> new CartAdvertisementDTO(advertisement)).collect(Collectors.toSet());
    }

}
