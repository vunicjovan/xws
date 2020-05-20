package com.uns.ftn.catalogservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CatalogController {

    @GetMapping("/")
    public ResponseEntity<?> getCatalog() {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> createCatalog() {
        return null;
    }

    @PostMapping("/adRequest")
    public ResponseEntity<?> createAdRequest() {
        return null;
    }

    @GetMapping("/adRequest")
    public ResponseEntity<?> getAdRequest() {
        return null;
    }

    @GetMapping("/adRequest/{id}")
    public ResponseEntity<?> getAllAdRequests(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/adRequest/{id}")
    public ResponseEntity<?> deleteAdRequest(@PathVariable("id") Long id) {
        return null;
    }

}
