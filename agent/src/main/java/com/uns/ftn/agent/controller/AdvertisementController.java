package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.domain.Advertisement;
import com.uns.ftn.agent.dto.AdvertisementDTO;
import com.uns.ftn.agent.service.AdvertisementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    private AdvertisementService advertisementService;

    public AdvertisementController(
            AdvertisementService advertisementService
    ) {
        this.advertisementService = advertisementService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> postAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementService.addNewAdvertisement(advertisementDTO);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllAds() {
        return null;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAd(@PathVariable Long id) {
        return null;
    }

}
