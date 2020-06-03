package com.uns.ftn.searchservice.service;

import com.uns.ftn.searchservice.domain.*;
import com.uns.ftn.searchservice.dto.AdvertisementDTO;
import com.uns.ftn.searchservice.repository.AdvertisementRepository;
import com.uns.ftn.searchservice.repository.PhotoRepository;
import com.uns.ftn.searchservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AdvertisementService {

 private AdvertisementRepository advertisementRepository;

 private VehicleRepository vehicleRepository;

 private PhotoRepository photoRepository;

 @Autowired
 public AdvertisementService(AdvertisementRepository advertisementRepository, VehicleRepository vehicleRepository,
                             PhotoRepository photoRepository) {
     this.advertisementRepository = advertisementRepository;
     this.vehicleRepository = vehicleRepository;
     this.photoRepository = photoRepository;
 }

 public void updateAdvertisement (AdvertisementDTO advertisementDTO) {
     Advertisement advertisement = new Advertisement();
     Vehicle vehicle = new Vehicle();

     vehicle.setId(advertisementDTO.getVehicle().getId());
     vehicle.setChildSeatNumber(advertisementDTO.getVehicle().getChildSeatNumber());
     vehicle.setKilometersTraveled(advertisementDTO.getVehicle().getKilometersTraveled());
     vehicle.setFuelTypeId(advertisementDTO.getVehicle().getFuelTypeId());
     vehicle.setGearboxTypeId(advertisementDTO.getVehicle().getGearboxTypeId());
     vehicle.setHasAndroid(advertisementDTO.getVehicle().getHasAndroid());
     vehicle.setModelId(advertisementDTO.getVehicle().getModelId());
     vehicle.setVehicleClassId(advertisementDTO.getVehicle().getVehicleClassId());
     vehicleRepository.save(vehicle);

     advertisement.setId(advertisementDTO.getId());
     advertisement.setVehicle(vehicle);
     advertisement.setOwnerId(advertisementDTO.getOwnerId());
     advertisement.setPrice(advertisementDTO.getPrice());
     advertisement.setCollisionDamageWaiver(advertisement.getCollisionDamageWaiver());
     advertisement.setDescription(advertisementDTO.getDescription());
     advertisement.setComments(new HashSet<Comment>());
     advertisement.setPhotos(new HashSet<Photo>());
     advertisement.setRentingIntervals(new HashSet<RentingInterval>());
     advertisement.setRating(advertisementDTO.getRating());
     advertisementRepository.save(advertisement);
 }

}
