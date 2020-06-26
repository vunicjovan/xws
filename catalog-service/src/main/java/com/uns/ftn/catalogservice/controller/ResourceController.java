package com.uns.ftn.catalogservice.controller;

import com.uns.ftn.catalogservice.dto.*;
import com.uns.ftn.catalogservice.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    private GearboxTypeService gbtService;
    private FuelTypeService fuelTypeService;
    private BrandService brandService;
    private VehicleClassService vehicleClassService;
    private ModelService modelService;

    @Autowired
    public ResourceController(VehicleClassService vehicleClassService, ModelService modelService,
                              GearboxTypeService gbtService, FuelTypeService fuelTypeService,
                              BrandService brandService) {
        this.vehicleClassService = vehicleClassService;
        this. modelService = modelService;
        this.gbtService = gbtService;
        this.fuelTypeService = fuelTypeService;
        this.brandService = brandService;
    }

    @PostMapping(value = "/gearboxType", consumes = "application/json")
    public ResponseEntity<?> addGearboxType(@RequestBody GearboxTypeDTO gbtDTO) {
        LOGGER.info("User has enter /gearboxType post endpoint");
        return gbtService.newGearboxType(gbtDTO);
    }
    
    @PostMapping(value = "/brand", consumes = "application/json")
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO) {
        LOGGER.info("User has enter /brand post endpoint");
        return brandService.newBrand(brandDTO);
    }

    @PostMapping("/fuelType")
    public ResponseEntity<?> addFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        LOGGER.info("User has enter /fuelType post endpoint");
        return new ResponseEntity<>(fuelTypeService.addFuelType(fuelTypeDTO), HttpStatus.CREATED);
    }

    @PostMapping("brand/{brandId}/model")
    public ResponseEntity<?> addModel(@PathVariable("brandId") Long id, @RequestBody ModelDTO modelDTO) {
        LOGGER.info("User has enter /brand/{}/model post endpoint", id);
        return modelService.newModel(id, modelDTO);
    }

    @PostMapping("/vehicleClass")
    public ResponseEntity<?> addVehicleClass(@RequestBody VehicleClassDTO vehicleClassDTO) {
        LOGGER.info("User has enter /vehicleClass post endpoint");
         return vehicleClassService.newVehicleClass(vehicleClassDTO);
    }

    @PutMapping(value = "/brand/{id}", consumes = "application/json")
    public ResponseEntity<?> updateBrand(@PathVariable("id") Long id, @RequestBody BrandDTO brandDTO) {
        LOGGER.info("User has enter /brand/{} put endpoint", id);
        return brandService.updateBrand(id, brandDTO);
    }

    @PutMapping("/fuelType/{id}")
    public ResponseEntity<?> updateFuelType(@PathVariable("id") Long id, @RequestBody FuelTypeDTO fuelTypeDTO) {
        LOGGER.info("User has enter /fuelType/{} put endpoint", id);
        return new ResponseEntity<>(fuelTypeService.updateFuelType(id, fuelTypeDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/gearboxType/{id}", consumes = "application/json")
    public ResponseEntity<?> updateGearboxType(@PathVariable("id") Long id, @RequestBody GearboxTypeDTO gbtDTO) {
        LOGGER.info("User has enter /gearboxType/{} put endpoint", id);
        return gbtService.updateGearboxType(gbtDTO, id);
    }

    @PutMapping("brand/{brandId}/model/{id}")
    public ResponseEntity<?> updateModel(@PathVariable("id") Long id, @PathVariable("brandId") Long brandId,
                                         @RequestBody ModelDTO modelDTO) {
        LOGGER.info("User has enter /brand/{}/model/{} put endpoint", brandId, id);
        return modelService.updateModel(id, brandId, modelDTO);
    }

    @PutMapping("/vehicleClass/{id}")
    public ResponseEntity<?> updateVehicleClass(@PathVariable("id") Long id,
                                                @RequestBody VehicleClassDTO vehicleClassDTO) {
        LOGGER.info("User has enter /vehicleClass/{} put endpoint", id);
        return vehicleClassService.updateVehicleClass(vehicleClassDTO);
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable("id") Long id) {
        LOGGER.info("User has enter /brand/{} delete endpoint", id);
        return brandService.deleteBrand(id);
    }

    @DeleteMapping("/fuelType/{id}")
    public ResponseEntity<?> deleteFuelType(@PathVariable("id") Long id) {
        LOGGER.info("User has enter /fuelType/{} delete endpoint", id);
        return new ResponseEntity<>(fuelTypeService.deleteFuelType(id), HttpStatus.OK);
    }

    @DeleteMapping("/gearboxType/{id}")
    public ResponseEntity<?> deleteGearboxType(@PathVariable("id") Long id) {
        LOGGER.info("User has enter /gearboxType/{} delete endpoint", id);
        return gbtService.deleteGearboxType(id);
    }

    @DeleteMapping("brand/{brandId}/model/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable("id") Long id, @PathVariable("brandId") Long brandId) {
        LOGGER.info("User has enter /brand/{}/model/{} delete endpoint", brandId, id);
        return modelService.deleteModel(id, brandId);
    }

    @DeleteMapping("/vehicleClass/{id}")
    public ResponseEntity<?> deleteVehicleClass(@PathVariable("id") Long id) {
        LOGGER.info("User has enter /vehicleClass/{} delete endpoint", id);
        return vehicleClassService.deleteVehicleClass(id);
    }

}
