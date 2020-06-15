package com.uns.ftn.agent.service;

import com.uns.ftn.agent.domain.*;
import com.uns.ftn.agent.dto.*;
import com.uns.ftn.agent.exceptions.NotFoundException;
import com.uns.ftn.agent.repository.*;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CatalogService {

    private ModelRepository modelRepository;
    private BrandRepository brandRepository;
    private FuelTypeRepository fuelTypeRepository;
    private GearboxTypeRepository gearboxTypeRepository;
    private VehicleClassRepository vehicleClassRepository;

    @Autowired
    public CatalogService(
            ModelRepository modelRepository,
            BrandRepository brandRepository,
            FuelTypeRepository fuelTypeRepository,
            GearboxTypeRepository gearboxTypeRepository,
            VehicleClassRepository vehicleClassRepository
    ) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.gearboxTypeRepository = gearboxTypeRepository;
        this.vehicleClassRepository = vehicleClassRepository;
    }

    public Model findOneModel(Long id) {
        return modelRepository.findById(id).orElseThrow(() -> new NotFoundException("Requested model doesn't exist."));
    }

    public Brand findOneBrand(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Requested brand doesn't exist."));
    }

    public FuelType findOneFuelType(Long id) {
        return fuelTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested fuel type doesn't exist."));
    }

    public GearboxType findOneGearboxType(Long id) {
        return gearboxTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested gearbox type doesn't exist."));
    }

    public VehicleClass findOneVehicleClass(Long id) {
        return vehicleClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested vehicle class doesn't exist."));
    }

    public ResponseEntity<?> getCatalog() {
        CatalogDTO catalog = new CatalogDTO();

        catalog.setBrands(brandRepository.findAll().stream().map(BrandDTO::new).collect(Collectors.toSet()));
        catalog.setFuelTypes(fuelTypeRepository.findAll().stream().map(FuelTypeDTO::new).collect(Collectors.toSet()));
        catalog.setGearboxTypes(gearboxTypeRepository.findAll().stream().map(GearboxTypeDTO::new)
                .collect(Collectors.toSet()));
        catalog.setVehicleClasses(vehicleClassRepository.findAll().stream().map(VehicleClassDTO::new)
                .collect(Collectors.toSet()));
        catalog.setModels(modelRepository.findAll().stream().map(ModelDTO::new).collect(Collectors.toSet()));

        return new ResponseEntity(catalog, HttpStatus.OK);
    }

}
