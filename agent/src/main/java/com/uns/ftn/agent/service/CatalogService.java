package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.CatalogClient;
import com.uns.ftn.agent.domain.*;
import com.uns.ftn.agent.dto.*;
import com.uns.ftn.agent.exceptions.NotFoundException;
import com.uns.ftn.agent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.GetCatalogResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private ModelRepository modelRepository;
    private BrandRepository brandRepository;
    private FuelTypeRepository fuelTypeRepository;
    private GearboxTypeRepository gearboxTypeRepository;
    private VehicleClassRepository vehicleClassRepository;
    private CatalogClient catalogClient;

    @Autowired
    public CatalogService(
            ModelRepository modelRepository,
            BrandRepository brandRepository,
            FuelTypeRepository fuelTypeRepository,
            GearboxTypeRepository gearboxTypeRepository,
            VehicleClassRepository vehicleClassRepository,
            CatalogClient catalogClient
    ) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.gearboxTypeRepository = gearboxTypeRepository;
        this.vehicleClassRepository = vehicleClassRepository;
        this.catalogClient = catalogClient;
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
        GetCatalogResponse response = catalogClient.getCatalog();

        if(response != null) {
            catalog.setBrands(response.getBrands().stream().map(BrandDTO::new).collect(Collectors.toSet()));
            catalog.setFuelTypes(response.getFuelTypes().stream().map(FuelTypeDTO::new).collect(Collectors.toSet()));
            catalog.setGearboxTypes(response.getGearboxTypes().stream().map(GearboxTypeDTO::new)
                    .collect(Collectors.toSet()));
            catalog.setVehicleClasses(response.getVehicleClasses().stream().map(VehicleClassDTO::new)
                    .collect(Collectors.toSet()));
            catalog.setModels(response.getModels().stream().map(ModelDTO::new).collect(Collectors.toSet()));

            //updateCatalog(catalog);
        } else {

        }
        return new ResponseEntity(catalog, HttpStatus.OK);
    }

    private void updateCatalog(CatalogDTO catalogDTO) {
        List<Brand> brands = catalogDTO.getBrands().stream().map(brandDTO -> {
            Brand brand = new Brand();
            brand.setId(brandDTO.getId());
            brand.setName(brandDTO.getName());
            return brand;
        }).collect(Collectors.toList());
        brandRepository.saveAll(brands);
    }

}
