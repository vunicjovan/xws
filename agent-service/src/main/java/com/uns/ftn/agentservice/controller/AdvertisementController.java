package com.uns.ftn.agentservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable ("id") Long id) {
        return null;
    }

    @GetMapping("/{id}/vehicle")
    public ResponseEntity<?> getVehicle(@PathVariable ("id") Long id) {
        return null;
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getAllComments(@PathVariable ("id") Long id) {
        return null;
    }

    @GetMapping("/{id}/comment/{comId}")
    public ResponseEntity<?> getComment(@PathVariable ("id") Long adId, @PathVariable ("comId") Long id) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> create() {
        return null;
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> postComment(@PathVariable ("id") Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ("id") Long id) {
        return null;
    }

    @PutMapping("/{id}/vehicle")
    public ResponseEntity<?> updateVehicle(@PathVariable ("id") Long id) {
        return null;
    }

    @PutMapping("/{id}/comment/{comId}")
    public ResponseEntity<?> approveComment(@PathVariable ("id") Long adId, @PathVariable ("comId") Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ("id") Long id) {
        return null;
    }

}
