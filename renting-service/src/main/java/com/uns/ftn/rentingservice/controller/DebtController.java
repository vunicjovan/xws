package com.uns.ftn.rentingservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debt")
public class DebtController {

    @GetMapping("/user/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> payDebt(@PathVariable("id") Long id) {
        return null;
    }

}
