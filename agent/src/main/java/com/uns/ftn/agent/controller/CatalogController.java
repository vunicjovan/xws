package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) { this.catalogService = catalogService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getCatalog() {
        logger.debug("Get catalog");
        return catalogService.getCatalog();
    }

}
