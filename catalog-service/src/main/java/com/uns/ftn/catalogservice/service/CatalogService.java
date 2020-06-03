package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.dto.CatalogDTO;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> checkResources(String resources) {
        String[] parts = resources.split("-");
        CheckResponseDTO crd = new CheckResponseDTO();

        try {
            GearboxType gbt = gearboxService.findOne(Long.parseLong(parts[2]));
            FuelType ft = resourceService.findOne(Long.parseLong(parts[1]));
            // findOne() for vehicle class
            // findOne() for vehicle model
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
