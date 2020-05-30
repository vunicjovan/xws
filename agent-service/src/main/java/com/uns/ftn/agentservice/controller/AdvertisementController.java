package com.uns.ftn.agentservice.controller;

import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    @Autowired
    private AdvertisementService adService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable ("id") Long id) {
        return null;
    }

    @GetMapping("/{id}/vehicle")
    public ResponseEntity<?> getVehicle(@PathVariable ("id") Long id) {
        return null;
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getAllComments(@PathVariable ("id") Long id) {
        return null;
    }

    @GetMapping("/{id}/comment/{comId}")
    public ResponseEntity<?> getComment(@PathVariable ("id") Long adId, @PathVariable ("comId") Long id) {
        return null;
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody AdvertisementDTO adDTO) {
        return adService.postNewAd(adDTO);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> postComment(@PathVariable ("id") Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ("id") Long id) {
        return null;
    }

    @PutMapping("/{id}/vehicle")
    public ResponseEntity<?> updateVehicle(@PathVariable ("id") Long id) {
        return null;
    }

    @PutMapping("/{id}/comment/{comId}")
    public ResponseEntity<?> approveComment(@PathVariable ("id") Long adId, @PathVariable ("comId") Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ("id") Long id) {
        return null;
    }

}
