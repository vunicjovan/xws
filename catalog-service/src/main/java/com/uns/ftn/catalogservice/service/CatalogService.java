package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.domain.*;
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

    public CatalogDTO getCatalogSOAP() {
        CatalogDTO catalogDTO = new CatalogDTO();
        Set<FuelTypeDTO> fuelTypeDTOSet = fuelTypeService.findAll();
        Set<GearboxTypeDTO> gearboxTypeDTOSet = gearboxService.findAll();
        Set<BrandDTO> brandDTOSet = brandService.findAll();
        Set<ModelDTO> modelDTOSet = modelService.findAll();
        Set<VehicleClassDTO> vehicleClassDTOSet = vehicleClassService.findAll();

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
            String status = checkResourceStatus(md.getBrand(), md, vc, ft, gbt);
            if (!status.equals("All good.")) {
                crd.setAvailable(false);
                crd.setMessage(status);
            } else {
                crd.setAvailable(true);
                crd.setMessage("All good.");
            }
        } catch (Exception e) {
            crd.setAvailable(false);
            crd.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

    /*
     * In case of deleted object, returns message with information about which type of catalog item is deleted.
     */
    private String checkResourceStatus(Brand brand, Model model, VehicleClass vehicleClass, FuelType fuelType,
                                       GearboxType gearboxType) {
        if (brand.getDeleted()) {
            return "Selected brand has been deleted.";
        } else if (model.getDeleted()) {
            return "Selected model has been deleted.";
        } else if (vehicleClass.getDeleted()) {
            return "Selected vehicle class has been deleted.";
        } else if (fuelType.getDeleted()) {
            return "Selected fuel type has been deleted.";
        } else if (gearboxType.getDeleted()) {
            return "Selected gearbox type has been deleted.";
        } else {
            return "All good.";
        }
    }

}
