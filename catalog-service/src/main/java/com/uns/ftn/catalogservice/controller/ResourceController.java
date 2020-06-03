package com.uns.ftn.catalogservice.controller;

import com.uns.ftn.catalogservice.dto.BrandDTO;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import com.uns.ftn.catalogservice.service.BrandService;
import com.uns.ftn.catalogservice.dto.VehicleClassDTO;
import com.uns.ftn.catalogservice.service.GearboxTypeService;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.service.ResourceService;
import com.uns.ftn.catalogservice.service.VehicleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResourceController {

    @Autowired
    private GearboxTypeService gbtService;
  
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private BrandService brandService;

    private VehicleClassService vehicleClassService;

    @Autowired
    public ResourceController(VehicleClassService vehicleClassService) {
        this.vehicleClassService = vehicleClassService;
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
        return new ResponseEntity<>(resourceService.addFuelType(fuelTypeDTO), HttpStatus.CREATED);
    }

    @PostMapping("/model")
    public ResponseEntity<?> addModel() {
        return null;
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
        return new ResponseEntity<>(resourceService.updateFuelType(id, fuelTypeDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/gearboxType/{id}", consumes = "application/json")
    public ResponseEntity<?> updateGearboxType(@PathVariable("id") Long id, @RequestBody GearboxTypeDTO gbtDTO) {
        return gbtService.updateGearboxType(gbtDTO, id);
    }

    @PutMapping("/model/{id}")
    public ResponseEntity<?> updateModel(@PathVariable("id") Long id) {
        return null;
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
        return new ResponseEntity<>(resourceService.deleteFuelType(id), HttpStatus.OK);
    }

    @DeleteMapping("/gearboxType/{id}")
    public ResponseEntity<?> deleteGearboxType(@PathVariable("id") Long id) {
        return gbtService.deleteGearboxType(id);
    }

    @DeleteMapping("/model/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/vehicleClass/{id}")
    public ResponseEntity<?> deleteVehicleClass(@PathVariable("id") Long id) {
        return vehicleClassService.deleteVehicleClass(id);
    }

}
