package com.uns.ftn.viewservice.service;

import com.uns.ftn.viewservice.domain.*;
import com.uns.ftn.viewservice.dto.*;
import com.uns.ftn.viewservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPumpService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    private GearboxTypeRepository gearboxTypeRepository;

    @Autowired
    private VehicleClassRepository vehicleClassRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;


    public void advertisementHandler(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = advertisementRepository.findById(advertisementDTO.getId()).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(advertisementDTO.getVehicle().getId()).orElse(null);

        if (advertisement == null) {
            advertisement = new Advertisement();
            advertisement.setId(advertisementDTO.getVehicle().getId());
        }

        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setId(advertisementDTO.getVehicle().getId());
        }
        advertisement.setPrice(advertisementDTO.getPrice());
        advertisement.setKilometersPerDayLimit(advertisementDTO.getKilometersPerDayLimit());
        advertisement.setCollisionDamageWaiver(advertisementDTO.getCollisionDamageWaiver());
        advertisement.setDescription(advertisementDTO.getDescription());
        advertisement.setOwnerId(advertisementDTO.getOwnerId());

        vehicle.setKilometersTraveled(advertisementDTO.getVehicle().getKilometersTraveled());
        vehicle.setChildSeatNumber(advertisementDTO.getVehicle().getChildSeatNumber());
        vehicle.setHasAndroid(advertisementDTO.getVehicle().getHasAndroid());
        vehicle.setFuelTypeId(advertisementDTO.getVehicle().getFuelTypeId());
        vehicle.setGearboxTypeId(advertisementDTO.getVehicle().getGearboxTypeId());
        vehicle.setVehicleClassId(advertisementDTO.getVehicle().getVehicleClassId());
        vehicle.setModelId(advertisementDTO.getVehicle().getModelId());

        vehicle.setAdvertisement(advertisement);

        advertisementRepository.save(advertisement);
        vehicleRepository.save(vehicle);
    }

    public void fuelTypeHandler(FuelTypeDTO fuelTypeDTO) {
        FuelType fuelType = fuelTypeRepository.findById(fuelTypeDTO.getId()).orElse(null);

        if (fuelType == null) {
            fuelType = new FuelType();
            fuelType.setId(fuelTypeDTO.getId());
        }

        fuelType.setName(fuelTypeDTO.getName());
        fuelType.setDeleted(fuelTypeDTO.getDeleted());

        fuelTypeRepository.save(fuelType);
    }

    public void gearboxTypeHandler(GearboxTypeDTO gearboxTypeDTO) {
        GearboxType gearboxType = gearboxTypeRepository.findById(gearboxTypeDTO.getId()).orElse(null);

        if (gearboxType == null) {
            gearboxType = new GearboxType();
            gearboxType.setId(gearboxTypeDTO.getId());
        }

        gearboxType.setName(gearboxTypeDTO.getName());
        gearboxType.setDeleted(gearboxTypeDTO.getDeleted());

        gearboxTypeRepository.save(gearboxType);
    }

    public void vehicleClassHandler(VehicleClassDTO vehicleClassDTO) {
        VehicleClass vehicleClass = vehicleClassRepository.findById(vehicleClassDTO.getId()).orElse(null);

        if (vehicleClass == null) {
            vehicleClass = new VehicleClass();
            vehicleClass.setId(vehicleClassDTO.getId());
        }

        vehicleClass.setName(vehicleClassDTO.getName());
        vehicleClass.setDeleted(vehicleClassDTO.getDeleted());

        vehicleClassRepository.save(vehicleClass);
    }

    public void brandHandler(BrandDTO brandDTO) {
        Brand brand = brandRepository.findById(brandDTO.getId()).orElse(null);

        if (brand == null) {
            brand = new Brand();
            brand.setId(brandDTO.getId());

            brand.setName(brandDTO.getName());
            brand.setDeleted(brandDTO.getDeleted());

            brandRepository.save(brand);
        }
    }

    public void modelHandler(ModelDTO modelDTO) {
        Model model = modelRepository.findById(modelDTO.getId()).orElse(null);
        Brand brand = brandRepository.findById(modelDTO.getId()).orElse(null);

        if (model == null) {
            model = new Model();
            model.setId(modelDTO.getId());
        }

        model.setName(modelDTO.getName());
        model.setDeleted(modelDTO.getDeleted());
        model.setBrand(brand);

        modelRepository.save(model);
    }

}
