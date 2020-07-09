package com.uns.ftn.rentingservice.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import com.uns.ftn.rentingservice.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debt")
public class DebtController {

    private DebtService debtService;

    @Autowired
    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> get(@PathVariable("userId") Long id) {

        return new ResponseEntity<>(debtService.getDebt(id), HttpStatus.OK);
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<?> getByAgent(@PathVariable("agentId") Long id) {

        return new ResponseEntity<>(debtService.getIncome(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> payDebt(@PathVariable("id") Long id) {

        return new ResponseEntity<>(debtService.payDebt(id), HttpStatus.OK);
    }

}
