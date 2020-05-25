package com.uns.ftn.accountservice.controller;

import com.uns.ftn.accountservice.dto.CompanyDTO;
import com.uns.ftn.accountservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        ArrayList<CompanyDTO> companyDTOArrayList = companyService.getAll();
        return new ResponseEntity<>(companyDTOArrayList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> registerCompany() {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
    }

}
