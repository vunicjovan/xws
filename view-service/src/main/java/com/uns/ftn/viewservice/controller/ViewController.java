package com.uns.ftn.viewservice.controller;

import com.uns.ftn.viewservice.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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

    @GetMapping("/client/{id}")
    public ResponseEntity<?> clientAd(@PathVariable("id") Long id) {
        return new ResponseEntity<>(viewService.getAdvert(id), HttpStatus.OK);
    }

    @GetMapping("/agent/{id}")
    public ResponseEntity<?> agentAds(@PathVariable("id") Long id) {
        return new ResponseEntity<>(viewService.getAgentsAdvertisements(id), HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        return new ResponseEntity<>(viewService.getCommentClient(id), HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> cartView(@RequestParam(value = "cart") List<Long> advertisementIdList) {
        return new ResponseEntity<>(viewService.getCartAdvertisements(advertisementIdList), HttpStatus.OK);
    }

}
