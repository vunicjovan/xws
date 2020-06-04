package com.uns.ftn.searchservice.service;

import com.netflix.discovery.converters.Auto;
import com.uns.ftn.searchservice.domain.*;
import com.uns.ftn.searchservice.dto.*;
import com.uns.ftn.searchservice.repository.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

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
        FuelType fuelType = new FuelType();

        fuelType.setId(fuelTypeDTO.getId());
        fuelType.setName(fuelTypeDTO.getName());
        fuelType.setDeleted(fuelTypeDTO.getDeleted());

        fuelTypeRepository.save(fuelType);
    }

    public void updateGearboxType(GearboxTypeDTO gearboxTypeDTO) {
        GearboxType gearboxType = new GearboxType();

        gearboxType.setId(gearboxTypeDTO.getId());
        gearboxType.setName(gearboxTypeDTO.getName());
        gearboxType.setDeleted(gearboxTypeDTO.getDeleted());

        gearboxTypeRepository.save(gearboxType);
    }

    public void updateBrand(BrandDTO brandDTO) {
        Brand brand = new Brand();

        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setDeleted(brandDTO.getDeleted());

        brandRepository.save(brand);
    }

    public void updateModel(ModelDTO modelDTO) {
        Model model = new Model();
        Brand brand = brandRepository.findById(modelDTO.getBrand().getId()).orElseGet(() -> null);

        if(brand != null) {
            model.setId(modelDTO.getId());
            model.setName(modelDTO.getName());
            model.setDeleted(modelDTO.getDeleted());
            model.setBrand(brand);

            modelRepository.save(model);
        }
    }

    public void updateVehicleClass(VehicleClassDTO vehicleClassDTO) {
        VehicleClass vehicleClass = new VehicleClass();

        vehicleClass.setId(vehicleClassDTO.getId());
        vehicleClass.setName(vehicleClassDTO.getName());
        vehicleClass.setDeleted(vehicleClassDTO.getDeleted());

        vehicleClassRepository.save(vehicleClass);
    }

    public FuelType findOneFuelType(Long id) { return fuelTypeRepository.findById(id).orElseGet(() -> null); }
    public GearboxType findOneGearboxType(Long id) { return gearboxTypeRepository.findById(id).orElseGet(() -> null); }
    public Brand findOneBrand(Long id) { return brandRepository.findById(id).orElseGet(() -> null); }
    public Model findOneModel(Long id) { return modelRepository.findById(id).orElseGet(() -> null); }
    public VehicleClass findOneVehicleClass(Long id) {
        return vehicleClassRepository.findById(id).orElseGet(() -> null);
    }
}
