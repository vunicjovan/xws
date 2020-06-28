package com.uns.ftn.agentservice.controller;

import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.dto.AdvertisementUpdateDTO;
import com.uns.ftn.agentservice.dto.CommDTO;
import com.uns.ftn.agentservice.dto.RatingDTO;
import com.uns.ftn.agentservice.service.AdvertisementService;
import com.uns.ftn.agentservice.service.CommentService;
import com.uns.ftn.agentservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    private AdvertisementService adService;
    private CommentService commentService;
    private final RatingService ratingService;

    @Autowired
    public AdvertisementController(AdvertisementService adService,
                                   CommentService commentService, RatingService ratingService) {
        this.adService = adService;
        this.commentService = commentService;
        this.ratingService = ratingService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable ("id") Long id) {
        return new ResponseEntity<>(new AdvertisementDTO(adService.findById(id)), HttpStatus.OK);
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

    @GetMapping("/comment/unapproved/")
    public ResponseEntity<?> getUnapprovedComments() { return commentService.getUnapprovedComments(); }

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

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody CommDTO commDTO) {
        return new ResponseEntity<>(commentService.postComment(commDTO), HttpStatus.OK);
    }

    @PostMapping("/{id}/vehicle/rate")
    public ResponseEntity<?> postRating(@PathVariable("id") Long adId, @RequestBody RatingDTO ratingDTO) {
        return new ResponseEntity<>(ratingService.rateAd(adId, ratingDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody AdvertisementUpdateDTO adto) {
        return adService.updateAdvertisement(id, adto);
    }

    @PutMapping("/{id}/vehicle")
    public ResponseEntity<?> updateVehicle(@PathVariable("id") Long id) {
        return null;
    }

    @PutMapping("/{id}/comment/{comId}")
    public ResponseEntity<?> approveComment(@PathVariable("id") Long adId, @PathVariable("comId") Long id) {
        return commentService.approveComment(adId, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ("id") Long id) {
        return null;
    }


    @DeleteMapping("/{adId}/comment/{id}")
    public ResponseEntity<?> rejectComment(@PathVariable("adId") Long adId, @PathVariable("id") Long id) {
        return commentService.rejectComment(adId, id);
    }

    @GetMapping("comment/{id}")
    public  ResponseEntity<?> getClientComment(@PathVariable ("id") Long id) {
        return new ResponseEntity<>(commentService.getClientComment(id), HttpStatus.OK);
    }
}
