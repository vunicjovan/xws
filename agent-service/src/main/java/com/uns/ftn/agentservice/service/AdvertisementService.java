package com.uns.ftn.agentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.agentservice.client.CatalogClient;
import com.uns.ftn.agentservice.components.QueueProducer;
import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.Vehicle;
import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.dto.CheckResponseDTO;
import com.uns.ftn.agentservice.repository.AdvertisementRepository;
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

    @Autowired
    private QueueProducer queueProducer;

    @Autowired
    private CatalogClient catalogClient;

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
        String checker = adDTO.getVehicle().getModelId() + "-" + adDTO.getVehicle().getFuelTypeId() + "-" +
                        adDTO.getVehicle().getGearboxTypeId() + "-" + adDTO.getVehicle().getVehicleClassId();

        CheckResponseDTO crd = catalogClient.checkIfResourcesExist(checker);
        if (!crd.getMessage().equals("All good.")) {
            return new ResponseEntity<>(crd.getMessage(), HttpStatus.NOT_FOUND);
        }

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

        vehicle.setAdvertisement(ad);

        // database injection: Photo, Vehicle, Advertisement
        adRepo.save(ad);
        vehicleRepo.save(vehicle);

        try {
            ad.setVehicle(vehicle);
            queueProducer.produce(new AdvertisementDTO(ad));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new AdvertisementDTO(ad), HttpStatus.CREATED);
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
