package com.uns.ftn.rentingservice.controller;

import com.uns.ftn.rentingservice.dto.RentingReportDTO;
import com.uns.ftn.rentingservice.service.RentingReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class RentingReportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentingReportController.class);

    private RentingReportService reportService;

    @Autowired
    public RentingReportController(RentingReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return null;
    }

    @PreAuthorize("hasAuthority('CREATE_RENT_REPORT')")
    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> createReport(@RequestBody RentingReportDTO rdto) {
        LOGGER.info("User requested creating new rentingReport[requestId={}, advertisementId={}]", rdto.getRequestID(),
                rdto.getAdvertisementID());
        return new ResponseEntity<>(reportService.createReport(rdto), HttpStatus.CREATED);
    }

}
