package com.uns.ftn.catalogservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.Brand;
import com.uns.ftn.catalogservice.dto.BrandDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.BrandRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    QueueProducer queueProducer;

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand findOne(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Requested brand does not exist."));
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findByName(String name) {
        return brandRepository.findByName(name);
    }

    public Set<BrandDTO> getAllBrands() {
        List<Brand> brandList = brandRepository.findAllByDeleted(false);

        return brandList.stream().sorted(Comparator.comparing(Brand::getName))
                .map(brand -> new BrandDTO(brand.getId(), brand.getName(), brand.getDeleted()))
                .collect(Collectors.toSet());
    }

    public ResponseEntity<?> newBrand(BrandDTO brandDTO) {
        if (!validatePostingData(brandDTO)) {
            throw new BadRequestException("Invalid format of brand name. Please try again with valid input.");
        }

        brandDTO.setName(Encode.forHtml(brandDTO.getName()));

        if (findByName(brandDTO.getName()) != null) {
            if (findByName(brandDTO.getName()).getDeleted()) {
                findByName(brandDTO.getName()).setDeleted(false);
                save(findByName(brandDTO.getName()));
                return new ResponseEntity<>(new BrandDTO(findByName(brandDTO.getName())), HttpStatus.CREATED);
            }
        }

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());

        try {
            brand = save(brand);
        } catch (Exception e) {
            throw new BadRequestException("Brand with requested name already exists.");
        }

        try {
            queueProducer.produceBrand(new BrandDTO(brand));
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        return new ResponseEntity<>(new BrandDTO(brand), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateBrand(Long id, BrandDTO brandDTO) {
        Brand brand = findOne(id);

        if (!validatePostingData(brandDTO)) {
            throw new BadRequestException("Invalid format of brand name. Please try again with valid input.");
        }

        brandDTO.setName(Encode.forHtml(brandDTO.getName()));

        brand.setName(brandDTO.getName());
        brand = save(brand);

        return new ResponseEntity<>(new BrandDTO(brand), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteBrand(Long id) {
        Brand brand = findOne(id);

        if (!brand.getModels().isEmpty()) {
            throw new BadRequestException("It is not possible to delete brand with existing models.");
        }

        brand.setDeleted(true);
        brand = save(brand);

        return new ResponseEntity<>(new BrandDTO(brand), HttpStatus.OK);
    }

    private Boolean validatePostingData(BrandDTO brandDTO) {
        String regex =
                "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([A-Z])+([a-zA-Z0-9\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        return brandDTO.getName() != null && !brandDTO.getName().trim().equals("") &&
                pattern.matcher(brandDTO.getName().trim()).matches();
    }

}
