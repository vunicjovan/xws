package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.dto.AdvertisementDTO;
import com.uns.ftn.agent.dto.CommentDTO;
import com.uns.ftn.agent.dto.PublisherCommentDTO;
import com.uns.ftn.agent.dto.RentingIntervalDTO;
import com.uns.ftn.agent.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> postAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        logger.debug("Posting new advertisement");
        return advertisementService.addNewAdvertisement(advertisementDTO);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllAds() {
        logger.debug("Get all advertisements");
        return new ResponseEntity<>(advertisementService.getDetailedAdvertisements(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAd(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}/statistic")
    public ResponseEntity<?> getStatisticReport(@PathVariable Long id) {
        logger.debug("Get statistic report");
        return advertisementService.returnStatisticReport(id);
    }

    @PostMapping("/interval/")
    public ResponseEntity<?> createRentingInterval(@RequestBody RentingIntervalDTO rentingIntervalDTO) {
        logger.debug("Add renting interval");
//        return rentingIntervalService.manuallyAddInterval(rentingIntervalDTO);
        return new ResponseEntity<>(advertisementService.manuallyAddInterval(rentingIntervalDTO), HttpStatus.OK);
    }

    @PostMapping("/publisher/comment")
    public ResponseEntity<?> publisherPostComment(@RequestBody PublisherCommentDTO commentDTO) {
        logger.debug("Post comment");
        return new ResponseEntity<>(advertisementService.publisherPostComment(commentDTO), HttpStatus.OK);
    }

}
