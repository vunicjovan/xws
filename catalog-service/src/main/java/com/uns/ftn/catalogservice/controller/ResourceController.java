package com.uns.ftn.catalogservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @PutMapping("/brand/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable("id") Long id) {
        return null;
    }

    @PutMapping("/fuelType/{id}")
    public ResponseEntity<?> updateFuelType(@PathVariable("id") Long id) {
        return null;
    }

    @PutMapping("/gearboxType/{id}")
    public ResponseEntity<?> updateGearboxType(@PathVariable("id") Long id) {
        return null;
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
        return null;
    }

    @DeleteMapping("/gearboxType/{id}")
    public ResponseEntity<?> deleteGearboxType(@PathVariable("id") Long id) {
        return null;
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
