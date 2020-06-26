package com.uns.ftn.agentservice.controller;

import com.uns.ftn.agentservice.dto.RentingIntervalDTO;
import com.uns.ftn.agentservice.service.RentingIntervalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interval")
public class RentingIntervalController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RentingIntervalService rentingIntervalService;

    @Autowired
    public RentingIntervalController(RentingIntervalService rentingIntervalService) {
        this.rentingIntervalService = rentingIntervalService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {return null;}

    @PostMapping("/")
    public ResponseEntity<?> createRentingInterval(@RequestBody RentingIntervalDTO rentingIntervalDTO) {
        logger.debug("Creating renting interval for advertisement with id {}", rentingIntervalDTO.getAdvertisementId());
//        return rentingIntervalService.manuallyAddInterval(rentingIntervalDTO);
        return new ResponseEntity<>(rentingIntervalService.manuallyAddInterval(rentingIntervalDTO), HttpStatus.OK);
    }

}
