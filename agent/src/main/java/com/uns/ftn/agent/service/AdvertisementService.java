package com.uns.ftn.agent.service;

import com.uns.ftn.agent.domain.Advertisement;
import com.uns.ftn.agent.domain.Vehicle;
import com.uns.ftn.agent.dto.AdvertisementDTO;
import com.uns.ftn.agent.exceptions.BadRequestException;
import com.uns.ftn.agent.repository.AdvertisementRepository;
import com.uns.ftn.agent.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.owasp.encoder.Encode;

import java.security.spec.EncodedKeySpec;
import java.util.regex.Pattern;

@Service
public class AdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private VehicleRepository vehicleRepository;
    private CatalogService catalogService;

    @Autowired
    public AdvertisementService(
            AdvertisementRepository advertisementRepository,
            VehicleRepository vehicleRepository,
            CatalogService catalogService
    ) {
        this.advertisementRepository = advertisementRepository;
        this.vehicleRepository = vehicleRepository;
        this.catalogService = catalogService;
    }

    public Advertisement saveAd(Advertisement advertisement) { return advertisementRepository.save(advertisement); }
    public Vehicle saveVehicle(Vehicle vehicle) { return vehicleRepository.save(vehicle); }

    public ResponseEntity<?> addNewAdvertisement(AdvertisementDTO adDTO) {

        if (!validateAdPostingData(adDTO)) {
            throw new BadRequestException("Invalid advertisement or vehicle data.");
        }

        adDTO.setDescription(Encode.forHtml(adDTO.getDescription()));
        adDTO.setLocation(Encode.forHtml(adDTO.getLocation()));

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(catalogService.findOneModel(adDTO.getVehicle().getModelId()));
        vehicle.setFuelType(catalogService.findOneFuelType(adDTO.getVehicle().getFuelTypeId()));
        vehicle.setGearboxType(catalogService.findOneGearboxType(adDTO.getVehicle().getGearboxTypeId()));
        vehicle.setVehicleClass(catalogService.findOneVehicleClass(adDTO.getVehicle().getVehicleClassId()));
        vehicle.setChildSeatNumber(adDTO.getVehicle().getChildSeatNumber());
        vehicle.setHasAndroid(adDTO.getVehicle().getHasAndroid());
        vehicle.setKilometersTraveled(adDTO.getVehicle().getKilometersTraveled());
        vehicle = saveVehicle(vehicle);

        Advertisement ad = new Advertisement();
        ad.setVehicle(vehicle);
        ad.setCollisionDamageWaiver(adDTO.getCollisionDamageWaiver());
        ad.setKilometersPerDayLimit(adDTO.getKilometersPerDayLimit());
        ad.setDescription(adDTO.getDescription());
        ad.setLocation(adDTO.getLocation());
        ad.setOwnerId(2L);
        ad.setRating(0.0);
        ad.setPrice(adDTO.getPrice());
        ad = saveAd(ad);

        return new ResponseEntity<>(new AdvertisementDTO(ad), HttpStatus.CREATED);
    }

    private Boolean validateAdPostingData(AdvertisementDTO adDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,;\\s?]+)$";
        String lrx = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\\s?)+$";
        Pattern pattern = Pattern.compile(regex);
        Pattern lpattern = Pattern.compile(lrx);

        if (adDTO.getDescription() == null || adDTO.getDescription().trim().equals("") ||
                !pattern.matcher(adDTO.getDescription().trim()).matches() || adDTO.getPrice() < 0 ||
                adDTO.getVehicle().getKilometersTraveled() < 0 || adDTO.getVehicle().getChildSeatNumber() < 0 ||
                adDTO.getLocation() == null || adDTO.getLocation().trim().equals("") ||
                !lpattern.matcher(adDTO.getLocation().trim()).matches()) {
            return false;
        }

        return true;

    }

}
