package com.uns.ftn.searchservice.service;

import com.netflix.discovery.converters.Auto;
import com.uns.ftn.searchservice.domain.*;
import com.uns.ftn.searchservice.dto.*;
import com.uns.ftn.searchservice.repository.*;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogService.class);

    private FuelTypeRepository fuelTypeRepository;
    private GearboxTypeRepository gearboxTypeRepository;
    private VehicleClassRepository vehicleClassRepository;
    private BrandRepository brandRepository;
    private ModelRepository modelRepository;

    @Autowired
    public CatalogService(FuelTypeRepository fuelTypeRepository,
                          GearboxTypeRepository gearboxTypeRepository,
                          VehicleClassRepository vehicleClassRepository,
                          BrandRepository brandRepository,
                          ModelRepository modelRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.gearboxTypeRepository = gearboxTypeRepository;
        this.vehicleClassRepository = vehicleClassRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
    }

    public void updateFuelType(FuelTypeDTO fuelTypeDTO) {
        LOGGER.debug("Fuel type data pump handler start fuelType[id={}, name={}]", fuelTypeDTO.getId(),
                fuelTypeDTO.getName());
        FuelType fuelType = new FuelType();

        fuelType.setId(fuelTypeDTO.getId());
        fuelType.setName(fuelTypeDTO.getName());
        fuelType.setDeleted(fuelTypeDTO.getDeleted());

        fuelTypeRepository.save(fuelType);
        LOGGER.info("Database entry: saved fuelType[id={}, name={}]", fuelType.getId(), fuelType.getName());
        LOGGER.debug("Fuel type data pump handler finish fuelType[id={}, name={}]", fuelType.getId(),
                fuelType.getName());
    }

    public void updateGearboxType(GearboxTypeDTO gearboxTypeDTO) {
        LOGGER.debug("Gearbox type data pump handler start gearboxType[id={}, name={}]", gearboxTypeDTO.getId(),
                gearboxTypeDTO.getName());
        GearboxType gearboxType = new GearboxType();

        gearboxType.setId(gearboxTypeDTO.getId());
        gearboxType.setName(gearboxTypeDTO.getName());
        gearboxType.setDeleted(gearboxTypeDTO.getDeleted());

        gearboxTypeRepository.save(gearboxType);
        LOGGER.info("Database entry: saved gearboxType[id={}, name={}]", gearboxType.getId(), gearboxType.getName());
        LOGGER.debug("Gearbox type data pump handler finish fuelType[id={}, name={}]", gearboxType.getId(),
                gearboxType.getName());
    }

    public void updateBrand(BrandDTO brandDTO) {
        LOGGER.debug("Brand data pump handler start brand[id={}, name={}]", brandDTO.getId(),
                brandDTO.getName());
        Brand brand = new Brand();

        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setDeleted(brandDTO.getDeleted());

        brandRepository.save(brand);
        LOGGER.info("Database entry: saved brand[id={}, name={}]", brand.getId(), brand.getName());
        LOGGER.debug("Brand data pump handler finish brand[id={}, name={}]", brand.getId(),
                brand.getName());
    }

    public void updateModel(ModelDTO modelDTO) {
        LOGGER.debug("Model data pump handler start model[id={}, name={}, brand={}]", modelDTO.getId(),
                modelDTO.getName(), modelDTO.getBrand().getName());
        Model model = new Model();
        Brand brand = brandRepository.findById(modelDTO.getBrand().getId()).orElseGet(() -> null);

        if(brand != null) {
            model.setId(modelDTO.getId());
            model.setName(modelDTO.getName());
            model.setDeleted(modelDTO.getDeleted());
            model.setBrand(brand);

            modelRepository.save(model);
            LOGGER.info("Database entry: saved model[id={}, name={}, brand={}]", modelDTO.getId(), modelDTO.getName(),
                    modelDTO.getBrand().getName());
            LOGGER.debug("Model data pump handler finish model[id={}, name={}, brand={}]", model.getId(),
                    model.getName(), model.getBrand().getName());
        }
    }

    public void updateVehicleClass(VehicleClassDTO vehicleClassDTO) {
        LOGGER.debug("Gearbox type data pump handler start vehicleClass[id={}, name={}]", vehicleClassDTO.getId(),
                vehicleClassDTO.getName());
        VehicleClass vehicleClass = new VehicleClass();

        vehicleClass.setId(vehicleClassDTO.getId());
        vehicleClass.setName(vehicleClassDTO.getName());
        vehicleClass.setDeleted(vehicleClassDTO.getDeleted());

        vehicleClassRepository.save(vehicleClass);
        LOGGER.info("Database entry: saved vehicleClass[id={}, name={}]", vehicleClass.getId(), vehicleClass.getName());
        LOGGER.debug("Gearbox type data pump handler finish vehicleClass[id={}, name={}]", vehicleClass.getId(),
                vehicleClass.getName());
    }

    public FuelType findOneFuelType(Long id) { return fuelTypeRepository.findById(id).orElseGet(() -> null); }
    public GearboxType findOneGearboxType(Long id) { return gearboxTypeRepository.findById(id).orElseGet(() -> null); }
    public Brand findOneBrand(Long id) { return brandRepository.findById(id).orElseGet(() -> null); }
    public Model findOneModel(Long id) { return modelRepository.findById(id).orElseGet(() -> null); }
    public VehicleClass findOneVehicleClass(Long id) {
        return vehicleClassRepository.findById(id).orElseGet(() -> null);
    }
}
