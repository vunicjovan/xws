package com.uns.ftn.searchservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SearchController {

    @GetMapping("/simple")
    public ResponseEntity<?> simpleSearch(@RequestParam(value = "address") String address,
                                          @RequestParam(value = "startDate") Date startDate,
                                          @RequestParam(value = "endDate") Date endDate) {
        return null;
    }

    @GetMapping("/advanced")
    public ResponseEntity<?> advancedSearch(@RequestParam(value = "address") String address,
                                            @RequestParam(value = "startDate") Date startDate,
                                            @RequestParam(value = "endDate") Date endDate,
                                            @RequestParam(value = "brand") Long brandId,
                                            @RequestParam(value = "model") Long modelId,
                                            @RequestParam(value = "fuel") Long fuelId,
                                            @RequestParam(value = "gearbox") Long gearboxId,
                                            @RequestParam(value = "class") Long classId,
                                            @RequestParam(value = "minPrice") double minPrice,
                                            @RequestParam(value = "maxPrice") double maxPrice,
                                            @RequestParam(value = "kmTraveled") double kmTraveled,
                                            @RequestParam(value = "kmPlaned") double kmPlaned,
                                            @RequestParam(value = "cdw") boolean cdw,
                                            @RequestParam(value = "childrenSeatNum") int childrenSeatNum) {
        return null;
    }


}
