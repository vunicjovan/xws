package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.dto.PriceListItemDTO;
import com.uns.ftn.agent.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/priceList")
public class PriceListController {

    private PriceListService priceListService;

    @Autowired
    public PriceListController(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getPriceList() {
        return new ResponseEntity<>(priceListService.getPriceList(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createPriceListItem(@RequestBody PriceListItemDTO priceListItemDTO) {
        return new ResponseEntity<>(priceListService.createPriceListItem(priceListItemDTO), HttpStatus.OK);
    }

    @PutMapping("/{discount}/")
    public ResponseEntity<?> createDiscount(@PathVariable("discount") Double discount) {
        System.out.println(discount);
        return new ResponseEntity<>(priceListService.createDiscount(discount), HttpStatus.OK);
    }

}
