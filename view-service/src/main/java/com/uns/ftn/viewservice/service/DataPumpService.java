package com.uns.ftn.viewservice.service;

import com.uns.ftn.viewservice.domain.*;
import com.uns.ftn.viewservice.dto.*;
import com.uns.ftn.viewservice.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class DataPumpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataPumpService.class);

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
        LOGGER.debug("Advertisement data pump handler start advertisement[id={}]", advertisementDTO.getId());
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
        LOGGER.info("Database entry: saved advertisement[id={}, brand={}, model={}]", advertisement.getId(),
                vehicle.getModel().getBrand().getName(), vehicle.getModel().getName());
        LOGGER.debug("Advertisement data pump handler finish advertisement[id={}]", advertisement.getId());
    }

    public void fuelTypeHandler(FuelTypeDTO fuelTypeDTO) {
        LOGGER.debug("Fuel type data pump handler start fuelType[id={}, name={}]", fuelTypeDTO.getId(),
                fuelTypeDTO.getName());
        FuelType fuelType = findFuelTypeById(fuelTypeDTO.getId());

        if (fuelType == null) {
            fuelType = new FuelType();
            fuelType.setId(fuelTypeDTO.getId());
        }

        fuelType.setName(fuelTypeDTO.getName());
        fuelType.setDeleted(fuelTypeDTO.getDeleted());

        fuelTypeRepository.save(fuelType);
        LOGGER.info("Database entry: saved fuelType[id={}, name={}]", fuelType.getId(), fuelType.getName());
        LOGGER.debug("Fuel type data pump handler finish fuelType[id={}, name={}]", fuelType.getId(),
                fuelType.getName());
    }

    public void gearboxTypeHandler(GearboxTypeDTO gearboxTypeDTO) {
        LOGGER.debug("Gearbox type data pump handler start gearboxType[id={}, name={}]", gearboxTypeDTO.getId(),
                gearboxTypeDTO.getName());
        GearboxType gearboxType = findGearboxTypeById(gearboxTypeDTO.getId());

        if (gearboxType == null) {
            gearboxType = new GearboxType();
            gearboxType.setId(gearboxTypeDTO.getId());
        }

        gearboxType.setName(gearboxTypeDTO.getName());
        gearboxType.setDeleted(gearboxTypeDTO.getDeleted());

        gearboxTypeRepository.save(gearboxType);
        LOGGER.info("Database entry: saved gearboxType[id={}, name={}]", gearboxType.getId(), gearboxType.getName());
        LOGGER.debug("Gearbox type data pump handler finish fuelType[id={}, name={}]", gearboxType.getId(),
                gearboxType.getName());
    }

    public void vehicleClassHandler(VehicleClassDTO vehicleClassDTO) {
        LOGGER.debug("Gearbox type data pump handler start vehicleClass[id={}, name={}]", vehicleClassDTO.getId(),
                vehicleClassDTO.getName());
        VehicleClass vehicleClass = findVehicleClassById(vehicleClassDTO.getId());

        if (vehicleClass == null) {
            vehicleClass = new VehicleClass();
            vehicleClass.setId(vehicleClassDTO.getId());
        }

        vehicleClass.setName(vehicleClassDTO.getName());
        vehicleClass.setDeleted(vehicleClassDTO.getDeleted());

        vehicleClassRepository.save(vehicleClass);
        LOGGER.info("Database entry: saved vehicleClass[id={}, name={}]", vehicleClass.getId(), vehicleClass.getName());
        LOGGER.debug("Gearbox type data pump handler finish vehicleClass[id={}, name={}]", vehicleClass.getId(),
                vehicleClass.getName());
    }

    public void brandHandler(BrandDTO brandDTO) {
        LOGGER.debug("Brand data pump handler start brand[id={}, name={}]", brandDTO.getId(),
                brandDTO.getName());
        Brand brand = findBrandById(brandDTO.getId());

        if (brand == null) {
            brand = new Brand();
            brand.setId(brandDTO.getId());
        }

        brand.setName(brandDTO.getName());
        brand.setDeleted(brandDTO.getDeleted());

        brandRepository.save(brand);
        LOGGER.info("Database entry: saved brand[id={}, name={}]", brand.getId(), brand.getName());
        LOGGER.debug("Brand data pump handler finish brand[id={}, name={}]", brand.getId(),
                brand.getName());

    }

    public void modelHandler(ModelDTO modelDTO) {
        LOGGER.debug("Model data pump handler start model[id={}, name={}, brand={}]", modelDTO.getId(),
                modelDTO.getName(), modelDTO.getBrand().getName());
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
        LOGGER.info("Database entry: saved model[id={}, name={}, brand={}]", modelDTO.getId(), modelDTO.getName(),
                modelDTO.getBrand().getName());
        LOGGER.debug("Model data pump handler finish model[id={}, name={}, brand={}]", model.getId(),
                model.getName(), model.getBrand().getName());
    }

    public void photoHandler(PhotoDTO photoDTO) {
        LOGGER.debug("Photo data pump handler start photo[id={}, path={}]", photoDTO.getId(), photoDTO.getPath());

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
        LOGGER.info("Database entry: saved photo[id={}, path={}]", photoDTO.getId(), photoDTO.getPath());
        LOGGER.debug("Photo data pump handler finish photo[id={}, path={}]", photo.getId(), photo.getPath());
    }

    public void commentHandler(CommDTO commDTO) {
        LOGGER.debug("Comment data pump handler start comment[id={}, title={}]", commDTO.getId(), commDTO.getTitle());
        Advertisement advertisement = findAdvertisementById(commDTO.getAdvertisementId());
        Comment comment = new Comment();
        comment.setId(commDTO.getId());
        comment.setTitle(commDTO.getTitle());
        comment.setContent(commDTO.getContent());
        comment.setAllowed(true);
        comment.setUserId(commDTO.getUserId());
        comment.setAdvertisement(advertisement);

        commentRepository.save(comment);
        LOGGER.info("Database entry: saved comment[id={}, title={}]", comment.getId(), comment.getTitle());
        LOGGER.debug("Comment data pump handler finish comment[id={}, title={}]", comment.getId(), comment.getTitle());
    }

    public void userHandler(UserDTO userDTO) {
        LOGGER.debug("User data pump handler start user[id={}]", userDTO.getId());
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
        LOGGER.info("Database entry: saved user[id={}]", user.getId());
        LOGGER.debug("User data pump handler finish user[id={}]", user.getId());
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
