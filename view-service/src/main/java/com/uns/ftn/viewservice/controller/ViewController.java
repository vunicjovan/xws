package com.uns.ftn.viewservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {

    @GetMapping("/")
    public ResponseEntity<?> standardView() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailedView(@PathVariable("id") Long id) {
        return null;
    }

}
