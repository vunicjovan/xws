package com.uns.ftn.rentingservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addItem(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/{cartId}/item/{id}")
    public ResponseEntity<?> delete(@PathVariable("cartId") Long cartId, @PathVariable("id") Long id) {
        return null;
    }

}
