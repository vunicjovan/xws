package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.Brand;
import com.uns.ftn.catalogservice.dto.BrandDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.BrandRepository;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandService.class);

    private BrandRepository brandRepository;
    private QueueProducer queueProducer;

    @Autowired
    public BrandService(BrandRepository brandRepository, QueueProducer queueProducer) {
        this.brandRepository = brandRepository;
        this.queueProducer = queueProducer;
    }

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand findOne(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> {
            LOGGER.warn("Database query: unable to find brand with id={}", id);
            return new NotFoundException("Requested brand does not exist.");
        });
    }

    public Set<BrandDTO> findAll() {
        return brandRepository.findAll().stream().map(BrandDTO::new).collect(Collectors.toSet());
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
        LOGGER.debug("Adding new brand...");
        if (!validatePostingData(brandDTO)) {
            LOGGER.warn("Invalid format for brand name={}", brandDTO.getName());
            throw new BadRequestException("Invalid format of brand name. Please try again with valid input.");
        }

        brandDTO.setName(Encode.forHtml(brandDTO.getName()));

        if (findByName(brandDTO.getName()) != null) {
            if (findByName(brandDTO.getName()).getDeleted()) {
                Brand brand = findByName(brandDTO.getName());
                LOGGER.warn("Restoring previously deleted brand[id={}, name={}]", brand.getId(), brand.getName());
                brand.setDeleted(false);
                save(brand);
                return new ResponseEntity<>(new BrandDTO(brand), HttpStatus.CREATED);
            }
        }

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());

        try {
            brand = save(brand);
            LOGGER.info("Database entry: created new brand[id={}, name={}]", brand.getId(), brand.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving new brand", e);
            throw new BadRequestException("Brand with requested name already exists.");
        }


        queueProducer.produceBrand(new BrandDTO(brand));
        LOGGER.debug("Finished adding new brand...");
        return new ResponseEntity<>(new BrandDTO(brand), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateBrand(Long id, BrandDTO brandDTO) {
        LOGGER.debug("Updating brand[id={}, name={}]", brandDTO.getId(), brandDTO.getName());
        Brand brand = findOne(id);

        if (!validatePostingData(brandDTO)) {
            LOGGER.warn("Invalid format for brand name={}", brandDTO.getName());
            throw new BadRequestException("Invalid format of brand name. Please try again with valid input.");
        }

        brandDTO.setName(Encode.forHtml(brandDTO.getName()));
        brand.setName(brandDTO.getName());

        try {
            brand = save(brand);
            LOGGER.info("Database update: updated brand[id={}, name={}]", brand.getId(), brand.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving updated brand", e);
            throw new BadRequestException("Brand with requested name already exists.");
        }

        queueProducer.produceBrand(new BrandDTO(brand));

        LOGGER.debug("Finished updating brand[id={}, name{}]", brand.getId(), brand.getName());
        return new ResponseEntity<>(new BrandDTO(brand), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteBrand(Long id) {
        Brand brand = findOne(id);
        LOGGER.debug("Deleting brand[id={}, name={}]", brand.getId(), brand.getName());

        if (!brand.getModels().isEmpty()) {
            LOGGER.warn("Unable to delete brand[id={}, name={}], existing models depend on brand", brand.getId(),
                    brand.getName());
            throw new BadRequestException("It is not possible to delete brand with existing models.");
        }

        brand.setDeleted(true);
        brand = save(brand);
        LOGGER.info("Database entry: deleted brand[id={}, name={}]", brand.getId(), brand.getName());

        queueProducer.produceBrand(new BrandDTO(brand));

        LOGGER.debug("Finished deleting brand[id={}, name={}]", brand.getId(), brand.getName());
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
