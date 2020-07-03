package com.uns.ftn.searchservice.controller;

import com.uns.ftn.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/simple")
    public ResponseEntity<?> simpleSearch(@RequestParam(value = "address") String address,
                                          @RequestParam(value = "startDate") Date startDate,
                                          @RequestParam(value = "endDate") Date endDate) {
        return searchService.simpleSearch(address, startDate, endDate);
    }

    @GetMapping("/advanced")
    public ResponseEntity<?> advancedSearch(@RequestParam(value = "address") String address,
                                            @RequestParam(value = "startDate") Date startDate,
                                            @RequestParam(value = "endDate") Date endDate,
                                            @RequestParam(value = "brand", required = false) Long brandId,
                                            @RequestParam(value = "model", required = false) Long modelId,
                                            @RequestParam(value = "fuel", required = false) Long fuelId,
                                            @RequestParam(value = "gearbox", required = false) Long gearboxId,
                                            @RequestParam(value = "class", required = false) Long classId,
                                            @RequestParam(value = "minPrice", required = false) Double minPrice,
                                            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                            @RequestParam(value = "kmTraveled", required = false) Integer kmTraveled,
                                            @RequestParam(value = "kmPlaned", required = false) Integer kmPlaned,
                                            @RequestParam(value = "cdw", required = false) Boolean cdw,
                                            @RequestParam(value = "childrenSeatNum", required = false) Integer childrenSeatNum) {
        return new ResponseEntity<>(searchService.advancedSearch(address, startDate, endDate, brandId, modelId, fuelId,
                gearboxId, classId, minPrice, maxPrice, kmTraveled, kmPlaned, cdw, childrenSeatNum), HttpStatus.OK);
    }


}
