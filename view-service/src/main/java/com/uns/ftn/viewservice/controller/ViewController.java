package com.uns.ftn.viewservice.controller;

import com.uns.ftn.viewservice.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public class ViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);

    private ViewService viewService;

    @Autowired
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/")
    public ResponseEntity<?> standardView() {
        LOGGER.info("User had requested all advertisements");
        return new ResponseEntity<>(viewService.getAllAdvertisements(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailedView(@PathVariable("id") Long id) {
        LOGGER.info("User had requested detailed info for advertisement[id={}]", id);
        return new ResponseEntity<>(viewService.getAdvertisement(id), HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> clientAd(@PathVariable("id") Long id) {
        LOGGER.info("Feign client had requested for owner of advertisement[id={}]", id);
        return new ResponseEntity<>(viewService.getAdvert(id), HttpStatus.OK);
    }

    @GetMapping("/agent/{id}")
    public ResponseEntity<?> agentAds(@PathVariable("id") Long id) {
        LOGGER.info("Feign client had requested advertisements for agent[id={}]", id);
        return new ResponseEntity<>(viewService.getAgentsAdvertisements(id), HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        LOGGER.info("User had requested comment[id={}]", id);
        return new ResponseEntity<>(viewService.getCommentClient(id), HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> cartView(@RequestParam(value = "cart") List<Long> advertisementIdList) {
        LOGGER.info("User had requested cart items.");
        return new ResponseEntity<>(viewService.getCartAdvertisements(advertisementIdList), HttpStatus.OK);
    }

}
