package com.uns.ftn.agentservice.controller;

import com.uns.ftn.agentservice.dto.PriceListItemDTO;
import com.uns.ftn.agentservice.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price/list")
public class PriceListController {

    private PriceListService priceListService;

    @Autowired
    public PriceListController(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id) {
        return priceListService.getAllItemsForUser(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody PriceListItemDTO pdto) {
        return new ResponseEntity<>(priceListService.createPriceListItem(pdto), HttpStatus.CREATED);
    }

    @PutMapping("/{ownerId}/{discount}")
    public ResponseEntity<?> createDiscount(@PathVariable Long ownerId, @PathVariable double discount) {
        return priceListService.createDiscount(ownerId, discount);
    }

}
