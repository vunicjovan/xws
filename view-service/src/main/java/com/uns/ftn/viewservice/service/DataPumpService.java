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

    @Autowired
    private PhotoRepository photoRepository;

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
        advertisement.setLocation(advertisementDTO.getLocation());

        vehicle.setKilometersTraveled(advertisementDTO.getVehicle().getKilometersTraveled());
        vehicle.setChildSeatNumber(advertisementDTO.getVehicle().getChildSeatNumber());
        vehicle.setHasAndroid(advertisementDTO.getVehicle().getHasAndroid());
        vehicle.setFuelType(findFuelTypeById(advertisementDTO.getVehicle().getFuelTypeId()));
        vehicle.setGearboxType(findGearboxTypeById(advertisementDTO.getVehicle().getGearboxTypeId()));
        vehicle.setVehicleClass(findVehicleClassById(advertisementDTO.getVehicle().getVehicleClassId()));
        vehicle.setModel(findModelById(advertisementDTO.getVehicle().getModelId()));

        vehicle.setAdvertisement(advertisement);

        advertisementRepository.save(advertisement);
        vehicleRepository.save(vehicle);
    }

    public void fuelTypeHandler(FuelTypeDTO fuelTypeDTO) {
        FuelType fuelType = findFuelTypeById(fuelTypeDTO.getId());

        if (fuelType == null) {
            fuelType = new FuelType();
            fuelType.setId(fuelTypeDTO.getId());
        }

        fuelType.setName(fuelTypeDTO.getName());
        fuelType.setDeleted(fuelTypeDTO.getDeleted());

        fuelTypeRepository.save(fuelType);
    }

    public void gearboxTypeHandler(GearboxTypeDTO gearboxTypeDTO) {
        GearboxType gearboxType = findGearboxTypeById(gearboxTypeDTO.getId());

        if (gearboxType == null) {
            gearboxType = new GearboxType();
            gearboxType.setId(gearboxTypeDTO.getId());
        }

        gearboxType.setName(gearboxTypeDTO.getName());
        gearboxType.setDeleted(gearboxTypeDTO.getDeleted());

        gearboxTypeRepository.save(gearboxType);
    }

    public void vehicleClassHandler(VehicleClassDTO vehicleClassDTO) {
        VehicleClass vehicleClass = findVehicleClassById(vehicleClassDTO.getId());

        if (vehicleClass == null) {
            vehicleClass = new VehicleClass();
            vehicleClass.setId(vehicleClassDTO.getId());
        }

        vehicleClass.setName(vehicleClassDTO.getName());
        vehicleClass.setDeleted(vehicleClassDTO.getDeleted());

        vehicleClassRepository.save(vehicleClass);
    }

    public void brandHandler(BrandDTO brandDTO) {
        Brand brand = findBrandById(brandDTO.getId());

        if (brand == null) {
            brand = new Brand();
            brand.setId(brandDTO.getId());

            brand.setName(brandDTO.getName());
            brand.setDeleted(brandDTO.getDeleted());

            brandRepository.save(brand);
        }
    }

    public void modelHandler(ModelDTO modelDTO) {
        Model model = findModelById(modelDTO.getId());
        Brand brand = findBrandById(modelDTO.getBrand().getId());

        if (model == null) {
            model = new Model();
            model.setId(modelDTO.getId());
        }

        model.setName(modelDTO.getName());
        model.setDeleted(modelDTO.getDeleted());
        model.setBrand(brand);

        modelRepository.save(model);
    }

    public void photoHandler(PhotoDTO photoDTO) {
        Photo photo = findPhotoById(photoDTO.getId());
        Advertisement ad = findAdvertisementById(photoDTO.getAdvertisement().getId());

        if (ad == null) {
            return;
        }

        if (photo == null) {
            photo = new Photo();
            photo.setId(photoDTO.getId());
        }

        photo.setPath(photoDTO.getPath());
        photo.setAdvertisement(ad);

        photoRepository.save(photo);
    }

    private FuelType findFuelTypeById(Long id) {
        return fuelTypeRepository.findById(id).orElse(null);
    }

    private GearboxType findGearboxTypeById(Long id) {
        return gearboxTypeRepository.findById(id).orElse(null);
    }

    private VehicleClass findVehicleClassById(Long id) {
        return vehicleClassRepository.findById(id).orElse(null);
    }

    private Model findModelById(Long id) {
        return modelRepository.findById(id).orElse(null);
    }

    private Brand findBrandById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    private Photo findPhotoById(Long id) { return photoRepository.findById(id).orElse(null); }

    private Advertisement findAdvertisementById(Long id) { return advertisementRepository.findById(id).orElse(null); }

}
