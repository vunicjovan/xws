package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.dto.RentingReportDTO;
import com.uns.ftn.agent.service.RentingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rent")
public class RentingController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private RentingService rentingService;

    @Autowired
    public RentingController(RentingService rentingService) {
        this.rentingService = rentingService;
    }

    @GetMapping(value = "/finishedRequests")
    public ResponseEntity<?> getFinishedRentingRequests()
    {
        logger.debug("Get finished requests");
        return this.rentingService.getFinishedRequests();
    }

    @PostMapping(value = "/report", consumes = "application/json")
    public ResponseEntity<?> compileRentingReport(@RequestBody RentingReportDTO rdto) {
        logger.debug("Compile renting report");
        return new ResponseEntity<>(rentingService.compileRentingReport(rdto), HttpStatus.CREATED);
    }

}
