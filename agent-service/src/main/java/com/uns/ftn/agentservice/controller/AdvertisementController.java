package com.uns.ftn.agentservice.controller;

import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    private AdvertisementService adService;

    @Autowired
    public AdvertisementController(AdvertisementService adService) {
        this.adService = adService;
    }

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
    public ResponseEntity<?> getAllComments(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/{id}/comment/{comId}")
    public ResponseEntity<?> getComment(@PathVariable("id") Long adId, @PathVariable("comId") Long id) {
        return null;
    }

    @GetMapping("/{id}/statistic")
    public ResponseEntity<?> getStatisticReport(@PathVariable Long id) {
        return adService.returnStatisticReport(id);
    }

    /* START: Endpoints for checking when deleting catalog item. */
    @GetMapping("/modelCheck/{id}")
    public ResponseEntity<?> checkAdsForModel(@PathVariable Long id) {
        return adService.checkForModel(id);
    }

    @GetMapping("/gearboxCheck/{id}")
    public ResponseEntity<?> checkAdsForGearbox(@PathVariable Long id) {
        return adService.checkForGearbox(id);
    }

    @GetMapping("/fuelCheck/{id}")
    public ResponseEntity<?> checkAdsForFuel(@PathVariable Long id) {
        return adService.checkForFuel(id);
    }

    @GetMapping("/classCheck/{id}")
    public ResponseEntity<?> checkAdsForClass(@PathVariable Long id) {
        return adService.checkForClass(id);
    }
    /* END: Endpoints for checking when deleting catalog item. */

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody AdvertisementDTO adDTO) {
        return adService.postNewAd(adDTO);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> postComment(@PathVariable("id") Long id) {
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
