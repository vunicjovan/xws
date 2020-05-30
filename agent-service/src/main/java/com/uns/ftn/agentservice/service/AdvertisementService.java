package com.uns.ftn.agentservice.service;

import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.Vehicle;
import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.repository.AdvertisementRepository;
import com.uns.ftn.agentservice.repository.PhotoRepository;
import com.uns.ftn.agentservice.repository.VehicleRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository adRepo;

    @Autowired
    private VehicleRepository vehicleRepo;


    public ResponseEntity<?> postNewAd(AdvertisementDTO adDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9\\\\!\\\\?\\\\#\\\\.\\\\,\\\\;\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        // data validation
        if (!validateAdPostingData(adDTO, pattern)) {
            return new ResponseEntity<>("Invalid advertisement or vehicle data.", HttpStatus.BAD_REQUEST);
        }

        // data sanitization
        adDTO.setDescription(Encode.forHtml(adDTO.getDescription()));

        // check if gearbox, fuel, class and model exist ---> catalog-service

        // setting advertisement properties
        Advertisement ad = new Advertisement();
        ad.setPrice(adDTO.getPrice());
        ad.setKilometersPerDayLimit(adDTO.getKilometersPerDayLimit());
        ad.setCollisionDamageWaiver(adDTO.getCollisionDamageWaiver());
        ad.setDescription(adDTO.getDescription());
        ad.setOwnerId(adDTO.getOwnerId());

        // setting vehicle properties
        Vehicle vehicle = new Vehicle();
        vehicle.setChildSeatNumber(adDTO.getVehicle().getChildSeatNumber());
        vehicle.setHasAndroid(adDTO.getVehicle().getHasAndroid());
        vehicle.setFuelTypeId(adDTO.getVehicle().getFuelTypeId());
        vehicle.setGearboxTypeId(adDTO.getVehicle().getGearboxTypeId());
        vehicle.setVehicleClassId(adDTO.getVehicle().getVehicleClassId());
        vehicle.setModelId(adDTO.getVehicle().getModelId());

        // creating vehicle photos

        //ad.setVehicle(vehicle);
        vehicle.setAdvertisement(ad);

        // database injection: Photo, Vehicle, Advertisement
        adRepo.save(ad);
        vehicleRepo.save(vehicle);

        return new ResponseEntity<>(adDTO, HttpStatus.CREATED);
    }

    /*
    * Returns TRUE if data is valid, else returns FALSE.
    */
    private Boolean validateAdPostingData(AdvertisementDTO adDTO, Pattern pattern) {
        if (adDTO.getDescription() == null || adDTO.getDescription().trim().equals("") ||
            !pattern.matcher(adDTO.getDescription().trim()).matches() || adDTO.getPrice() < 0 ||
            adDTO.getVehicle().getKilometersTraveled() < 0 || adDTO.getVehicle().getChildSeatNumber() < 0 /*||
            adDTO.getPhotos().size() <= 0*/) {
            return false;
        }

        return true;
    }

    public Advertisement findById(Long id) {
        return adRepo.getOne(id);
    }

}
