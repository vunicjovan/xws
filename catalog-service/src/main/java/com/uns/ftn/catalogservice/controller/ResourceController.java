package com.uns.ftn.catalogservice.controller;

import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import com.uns.ftn.catalogservice.service.GearboxTypeService;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.service.ResourceService;
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

    @PostMapping(value = "/gearboxType", consumes = "application/json")
    public ResponseEntity<?> addGearboxType(@RequestBody GearboxTypeDTO gbtDTO) {
        return gbtService.newGearboxType(gbtDTO);
    }
    
    @PostMapping("/brand")
    public ResponseEntity<?> addBrand() {
        return null;
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
    public ResponseEntity<?> addVehicleClass() {
        return null;
    }

    @PutMapping("/brand/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable("id") Long id) {
        return null;
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
    public ResponseEntity<?> updateVehicleClass(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable("id") Long id) {
        return null;
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
        return null;
    }

}
