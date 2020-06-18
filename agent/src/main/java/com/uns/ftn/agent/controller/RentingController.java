package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.dto.RentingReportDTO;
import com.uns.ftn.agent.service.RentingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rent")
public class RentingController {

    private RentingService rentingService;

    @Autowired
    public RentingController(RentingService rentingService) {
        this.rentingService = rentingService;
    }

    @GetMapping(value = "/finishedRequests")
    public ResponseEntity<?> getFinishedRentingRequests() {
        return this.rentingService.getFinishedRequests();
    }

    @PostMapping(value = "/report", consumes = "application/json")
    public ResponseEntity<?> compileRentingReport(@RequestBody RentingReportDTO rdto) {
        return new ResponseEntity<>(rentingService.compileRentingReport(rdto), HttpStatus.CREATED);
    }

}
