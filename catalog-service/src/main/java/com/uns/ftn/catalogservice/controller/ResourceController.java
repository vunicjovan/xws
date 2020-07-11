package com.uns.ftn.catalogservice.controller;

import com.uns.ftn.catalogservice.dto.*;
import com.uns.ftn.catalogservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResourceController {

    private GearboxTypeService gbtService;
    private FuelTypeService fuelTypeService;
    private BrandService brandService;
    private VehicleClassService vehicleClassService;
    private ModelService modelService;

    @Autowired
    public ResourceController(VehicleClassService vehicleClassService, ModelService modelService, GearboxTypeService gbtService, FuelTypeService fuelTypeService, BrandService brandService) {
        this.vehicleClassService = vehicleClassService;
        this.modelService = modelService;
        this.gbtService = gbtService;
        this.fuelTypeService = fuelTypeService;
        this.brandService = brandService;
    }

    @PostMapping(value = "/gearboxType", consumes = "application/json")
    public ResponseEntity<?> addGearboxType(@RequestBody GearboxTypeDTO gbtDTO) {
        return gbtService.newGearboxType(gbtDTO);
    }
    
    @PostMapping(value = "/brand", consumes = "application/json")
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO) {
        return brandService.newBrand(brandDTO);
    }

    @PostMapping("/fuelType")
    public ResponseEntity<?> addFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return new ResponseEntity<>(fuelTypeService.addFuelType(fuelTypeDTO), HttpStatus.CREATED);
    }

    @PostMapping("brand/{brandId}/model")
    public ResponseEntity<?> addModel(@PathVariable("brandId") Long id, @RequestBody ModelDTO modelDTO) {
        return modelService.newModel(id, modelDTO);
    }

    @PostMapping("/vehicleClass")
    public ResponseEntity<?> addVehicleClass(@RequestBody VehicleClassDTO vehicleClassDTO) {
         return vehicleClassService.newVehicleClass(vehicleClassDTO);
    }

    @PutMapping(value = "/brand/{id}", consumes = "application/json")
    public ResponseEntity<?> updateBrand(@PathVariable("id") Long id, @RequestBody BrandDTO brandDTO) {
        return brandService.updateBrand(id, brandDTO);
    }

    @PutMapping("/fuelType/{id}")
    public ResponseEntity<?> updateFuelType(@PathVariable("id") Long id, @RequestBody FuelTypeDTO fuelTypeDTO) {
        return new ResponseEntity<>(fuelTypeService.updateFuelType(id, fuelTypeDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/gearboxType/{id}", consumes = "application/json")
    public ResponseEntity<?> updateGearboxType(@PathVariable("id") Long id, @RequestBody GearboxTypeDTO gbtDTO) {
        return gbtService.updateGearboxType(gbtDTO, id);
    }

    @PutMapping("brand/{brandId}/model/{id}")
    public ResponseEntity<?> updateModel(@PathVariable("id") Long id, @PathVariable("brandId") Long brandId,
                                         @RequestBody ModelDTO modelDTO) {
        return modelService.updateModel(id, brandId, modelDTO);
    }

    @PutMapping("/vehicleClass/{id}")
    public ResponseEntity<?> updateVehicleClass(@PathVariable("id") Long id,
                                                @RequestBody VehicleClassDTO vehicleClassDTO) {
        return vehicleClassService.updateVehicleClass(vehicleClassDTO);
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable("id") Long id) {
        return brandService.deleteBrand(id);
    }

    @DeleteMapping("/fuelType/{id}")
    public ResponseEntity<?> deleteFuelType(@PathVariable("id") Long id) {
        return new ResponseEntity<>(fuelTypeService.deleteFuelType(id), HttpStatus.OK);
    }

    @DeleteMapping("/gearboxType/{id}")
    public ResponseEntity<?> deleteGearboxType(@PathVariable("id") Long id) {
        return gbtService.deleteGearboxType(id);
    }

    @DeleteMapping("brand/{brandId}/model/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable("id") Long id, @PathVariable("brandId") Long brandId) {
        return modelService.deleteModel(id, brandId);
    }

    @DeleteMapping("/vehicleClass/{id}")
    public ResponseEntity<?> deleteVehicleClass(@PathVariable("id") Long id) {
        return vehicleClassService.deleteVehicleClass(id);
    }

}
