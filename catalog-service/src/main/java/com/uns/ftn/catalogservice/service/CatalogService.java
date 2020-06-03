package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CatalogService {

    @Autowired
    private FuelTypeService fuelTypeService;

    @Autowired
    private GearboxTypeService gearboxService;

    @Autowired
    private BrandService brandService;

    private VehicleClassService vehicleClassService;
    private ModelService modelService;

    @Autowired
    public CatalogService(VehicleClassService vehicleClassService, ModelService modelService) {
        this.vehicleClassService = vehicleClassService;
        this.modelService = modelService;
    }

    public CatalogDTO getCatalog() {
        CatalogDTO catalogDTO = new CatalogDTO();
        Set<FuelTypeDTO> fuelTypeDTOSet = fuelTypeService.getAllFuelTypes();
        Set<GearboxTypeDTO> gearboxTypeDTOSet = gearboxService.getAllGearboxTypes();
        Set<BrandDTO> brandDTOSet = brandService.getAllBrands();
        Set<ModelDTO> modelDTOSet = modelService.getAllModels();
        Set<VehicleClassDTO> vehicleClassDTOSet = vehicleClassService.getAllVehicleClasses();
        
        catalogDTO.setFuelTypes(fuelTypeDTOSet);
        catalogDTO.setGearboxTypes(gearboxTypeDTOSet);
        catalogDTO.setBrands(brandDTOSet);
        catalogDTO.setModels(modelDTOSet);
        catalogDTO.setVehicleClasses(vehicleClassDTOSet);

        return catalogDTO;
    }

}
