package com.uns.ftn.rentingservice.controller;

import com.uns.ftn.rentingservice.domain.RequestStatus;
import com.uns.ftn.rentingservice.dto.RentingRequestDTO;
import com.uns.ftn.rentingservice.dto.RequestStatusDTO;
import com.uns.ftn.rentingservice.service.RentingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/request")
public class RentingRequestController {

    private RentingRequestService requestService;

    @Autowired
    public RentingRequestController(RentingRequestService requestService) {
        this.requestService = requestService;
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
    public ResponseEntity<?> createRequest(@RequestBody RentingRequestDTO rdto) {
        return this.requestService.createRequest(rdto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestBody RequestStatusDTO request) {
        return requestService.updateRequestStatus(id, request);
    }

}
