package com.uns.ftn.viewservice.service;

import com.uns.ftn.viewservice.domain.*;
import com.uns.ftn.viewservice.dto.*;
import com.uns.ftn.viewservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class DataPumpService {

    private final AdvertisementRepository advertisementRepository;
    private final VehicleRepository vehicleRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final GearboxTypeRepository gearboxTypeRepository;
    private final VehicleClassRepository vehicleClassRepository;
    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final PhotoRepository photoRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataPumpService(AdvertisementRepository advertisementRepository, VehicleRepository vehicleRepository, FuelTypeRepository fuelTypeRepository, GearboxTypeRepository gearboxTypeRepository, VehicleClassRepository vehicleClassRepository, ModelRepository modelRepository, BrandRepository brandRepository, PhotoRepository photoRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.advertisementRepository = advertisementRepository;
        this.vehicleRepository = vehicleRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.gearboxTypeRepository = gearboxTypeRepository;
        this.vehicleClassRepository = vehicleClassRepository;
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.photoRepository = photoRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public void advertisementHandler(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = advertisementRepository.findById(advertisementDTO.getId()).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(advertisementDTO.getVehicle().getId()).orElse(null);

        if (advertisement == null) {
            advertisement = new Advertisement();
            advertisement.setId(advertisementDTO.getId());
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
        advertisement.setDeleted(advertisementDTO.getDeleted());
//        advertisement.setRatedByUsers(advertisementDTO.getRatedByUsers());

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
        Advertisement ad = findAdvertisementById(photoDTO.getAdvertisementId());

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

    public void commentHandler(CommDTO commDTO) {
        Advertisement advertisement = findAdvertisementById(commDTO.getAdvertisementId());
        Comment comment = new Comment();
        comment.setId(commDTO.getId());
        comment.setTitle(commDTO.getTitle());
        comment.setContent(commDTO.getContent());
        comment.setAllowed(true);
        comment.setUserId(commDTO.getUserId());
        comment.setAdvertisement(advertisement);

        commentRepository.save(comment);
    }

    public void userHandler(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserId(userDTO.getUserId());
        user.setRatedAds(new HashSet<>());


//        user.setRatedAds(userDTO.getRatedAds());

        userDTO.getRatedAds().forEach(a -> {
            Advertisement advertisement = findAdvertisementById(a.getId());
//            advertisement.getRatedByUsers().add(finalUser);
            if (advertisement != null && !user.getRatedAds().contains(advertisement)) {
                user.getRatedAds().add(advertisement);
            }
//            advertisementRepository.save(advertisement);
        });

        userRepository.save(user);
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
