package com.uns.ftn.searchservice.service;

import com.uns.ftn.searchservice.domain.*;
import com.uns.ftn.searchservice.dto.AdvertisementDTO;
import com.uns.ftn.searchservice.dto.PhotoDTO;
import com.uns.ftn.searchservice.repository.AdvertisementRepository;
import com.uns.ftn.searchservice.repository.PhotoRepository;
import com.uns.ftn.searchservice.repository.VehicleRepository;
import com.uns.ftn.searchservice.specifications.AdvertisementSpecs;
import com.uns.ftn.searchservice.specifications.SearchCriteria;
import org.apache.commons.configuration.resolver.CatalogResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class AdvertisementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementService.class);

    private AdvertisementRepository advertisementRepository;
    private VehicleRepository vehicleRepository;
    private PhotoRepository photoRepository;
    private CatalogService catalogService;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository,
                                VehicleRepository vehicleRepository,
                                PhotoRepository photoRepository,
                                CatalogService catalogService) {
        this.advertisementRepository = advertisementRepository;
        this.vehicleRepository = vehicleRepository;
        this.photoRepository = photoRepository;
        this.catalogService = catalogService;
    }

    public void updateAdvertisement(AdvertisementDTO advertisementDTO) {
        LOGGER.debug("Advertisement data pump handler start advertisement[id={}]", advertisementDTO.getId());
        Advertisement advertisement = new Advertisement();
        Vehicle vehicle = new Vehicle();

        vehicle.setId(advertisementDTO.getVehicle().getId());
        vehicle.setChildSeatNumber(advertisementDTO.getVehicle().getChildSeatNumber());
        vehicle.setKilometersTraveled(advertisementDTO.getVehicle().getKilometersTraveled());
        vehicle.setFuelType(catalogService.findOneFuelType(advertisementDTO.getVehicle().getFuelTypeId()));
        vehicle.setGearboxType(catalogService.findOneGearboxType(advertisementDTO.getVehicle().getGearboxTypeId()));
        vehicle.setHasAndroid(advertisementDTO.getVehicle().getHasAndroid());
        vehicle.setModel(catalogService.findOneModel(advertisementDTO.getVehicle().getModelId()));
        vehicle.setVehicleClass(catalogService.findOneVehicleClass(advertisementDTO.getVehicle().getVehicleClassId()));
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
        advertisement.setLocation(advertisementDTO.getLocation());
        advertisementRepository.save(advertisement);

        LOGGER.info("Database entry: saved advertisement[id={}, brand={}, model={}]", advertisement.getId(),
                vehicle.getModel().getBrand().getName(), vehicle.getModel().getName());
        LOGGER.debug("Advertisement data pump handler finish advertisement[id={}]", advertisement.getId());
    }

    public void updatePhoto(PhotoDTO photoDTO) {
        LOGGER.debug("Photo data pump handler start photo[id={}, path={}]", photoDTO.getId(), photoDTO.getPath());
        Photo photo = new Photo();
        Advertisement advertisement = advertisementRepository.
                findById(photoDTO.getAdvertisementId()).
                orElse(null);

        if (advertisement == null) {
            return;
        }

        photo.setId(photoDTO.getId());
        photo.setPath(photoDTO.getPath());
        photo.setAdvertisement(advertisement);

        photoRepository.save(photo);
        LOGGER.info("Database entry: saved photo[id={}, path={}]", photoDTO.getId(), photoDTO.getPath());
        LOGGER.debug("Photo data pump handler finish photo[id={}, path={}]", photo.getId(), photo.getPath());
    }

    public List<Advertisement> findByLocationLike(String location) {
        AdvertisementSpecs spec =
                new AdvertisementSpecs(new SearchCriteria("location", ":", location));

        return advertisementRepository.findAll(spec);
    }

}
