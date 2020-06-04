package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.domain.FuelType;
import com.uns.ftn.catalogservice.domain.GearboxType;
import com.uns.ftn.catalogservice.domain.Model;
import com.uns.ftn.catalogservice.domain.VehicleClass;
import com.uns.ftn.catalogservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CatalogService {

    private FuelTypeService fuelTypeService;
    private GearboxTypeService gearboxService;
    private BrandService brandService;
    private VehicleClassService vehicleClassService;
    private ModelService modelService;

    @Autowired
    public CatalogService(VehicleClassService vehicleClassService, ModelService modelService,
                          FuelTypeService fuelTypeService, GearboxTypeService gearboxService, BrandService brandService) {
        this.vehicleClassService = vehicleClassService;
        this.modelService = modelService;
        this.fuelTypeService = fuelTypeService;
        this.gearboxService = gearboxService;
        this.brandService = brandService;
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

    public ResponseEntity<?> checkResources(String resources) {
        String[] parts = resources.split("-");
        CheckResponseDTO crd = new CheckResponseDTO();

        try {
            Model md = modelService.findOne(Long.parseLong(parts[0]));
            FuelType ft = fuelTypeService.findOne(Long.parseLong(parts[1]));
            GearboxType gbt = gearboxService.findOne(Long.parseLong(parts[2]));
            VehicleClass vc = vehicleClassService.findOne(Long.parseLong(parts[3]));
            crd.setAvailable(true);
            crd.setMessage("All good.");
        }
        catch (Exception e) {
            crd.setAvailable(false);
            crd.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

}
