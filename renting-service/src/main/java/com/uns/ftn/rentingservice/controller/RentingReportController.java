package com.uns.ftn.rentingservice.controller;

import com.uns.ftn.rentingservice.dto.RentingReportDTO;
import com.uns.ftn.rentingservice.service.RentingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class RentingReportController {

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

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> createReport(@RequestBody RentingReportDTO rdto) {
        return new ResponseEntity<>(reportService.createReport(rdto), HttpStatus.CREATED);
    }

}
