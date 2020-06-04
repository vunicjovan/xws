package com.uns.ftn.catalogservice.controller;

import com.uns.ftn.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/")
    public ResponseEntity<?> getCatalog()
    {
        return new ResponseEntity<>(catalogService.getCatalog(), HttpStatus.OK);
    }

    @GetMapping("/resourceCheck/{resources}")
    public ResponseEntity<?> checkIfResourcesExist(@PathVariable String resources) {
        return catalogService.checkResources(resources);
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
