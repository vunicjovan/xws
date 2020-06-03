package com.uns.ftn.viewservice.controller;

import com.uns.ftn.viewservice.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {

    @Autowired
    private ViewService viewService;

    @GetMapping("/")
    public ResponseEntity<?> standardView() {
        return new ResponseEntity<>(viewService.getAllAdvertisements(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailedView(@PathVariable("id") Long id) {
        return new ResponseEntity<>(viewService.getAdvertisement(id), HttpStatus.OK);
    }

}
