package com.uns.ftn.viewservice.controller;

import com.uns.ftn.viewservice.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/cart")
    public ResponseEntity<?> cartView(@RequestParam(value = "cart") List<Long> advertisementIdList) {
        return new ResponseEntity<>(viewService.getCartAdvertisements(advertisementIdList), HttpStatus.OK);
    }

}
