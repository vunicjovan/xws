package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.dto.AdvertisementDTO;
import com.uns.ftn.agent.dto.PublisherCommentDTO;
import com.uns.ftn.agent.dto.RentingIntervalDTO;
import com.uns.ftn.agent.dto.UpdateAdvertisementDTO;
import com.uns.ftn.agent.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    private AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> postAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementService.addNewAdvertisement(advertisementDTO);
    }

    @PutMapping(value = "/")
    public ResponseEntity<?> updateAdvertisement(@RequestBody UpdateAdvertisementDTO updateAdvertisementDTO) {
        return new ResponseEntity<>(advertisementService.updateAdvertisement(updateAdvertisementDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllAds() {
        return new ResponseEntity<>(advertisementService.getDetailedAdvertisements(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAd(@PathVariable Long id) {
        return new ResponseEntity<>(advertisementService.getOneDetailedAdvertisement(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/statistic")
    public ResponseEntity<?> getStatisticReport(@PathVariable Long id) {
        return advertisementService.returnStatisticReport(id);
    }

    @PostMapping("/interval/")
    public ResponseEntity<?> createRentingInterval(@RequestBody RentingIntervalDTO rentingIntervalDTO) {
//        return rentingIntervalService.manuallyAddInterval(rentingIntervalDTO);
        return new ResponseEntity<>(advertisementService.manuallyAddInterval(rentingIntervalDTO), HttpStatus.OK);
    }

    @PostMapping("/publisher/comment")
    public ResponseEntity<?> publisherPostComment(@RequestBody PublisherCommentDTO commentDTO) {
        return new ResponseEntity<>(advertisementService.publisherPostComment(commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertisement(@PathVariable("id") Long id) {
        return new ResponseEntity<>(advertisementService.deleteAdvertisement(id), HttpStatus.OK);
    }

}
