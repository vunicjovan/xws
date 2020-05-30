package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.domain.FuelType;
import com.uns.ftn.catalogservice.domain.GearboxType;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.GearboxTypeRepository;
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
public class GearboxTypeService {

    @Autowired
    private GearboxTypeRepository gearboxRepo;

    public GearboxType save(GearboxType gbt) {
        return gearboxRepo.save(gbt);
    }

    public GearboxType findOne(Long id) {
        return gearboxRepo.findById(id).orElseThrow(() -> new NotFoundException("Requested gearbox type does not exist."));
    }

    public List<GearboxType> findAll() {
        return gearboxRepo.findAll();
    }

    public GearboxType findByName(String name) {
        return gearboxRepo.findByName(name);
    }

    public Set<GearboxTypeDTO> getAllGearboxTypes() {
        List<GearboxType> gearboxTypeList = gearboxRepo.findAllByDeleted(false);

        return gearboxTypeList.stream().sorted(Comparator.comparing(GearboxType::getName))
                .map(gearboxType -> new GearboxTypeDTO(gearboxType.getId(), gearboxType.getName())).collect(Collectors.toSet());
    }

    /*
    * Adds new gearbox type to catalog, if possible.
    */
    public ResponseEntity<?> newGearboxType(GearboxTypeDTO gbtDTO) {
        // data validation
        if (!validatePostingData(gbtDTO)) {
            throw new BadRequestException("Invalid format of gearbox type name. Please try again with valid input.");
        }

        // data sanitization
        gbtDTO.setName(Encode.forHtml(gbtDTO.getName()));

        GearboxType gbt = new GearboxType();
        gbt.setName(gbtDTO.getName());

        try {
            gbt = save(gbt);
        }
        catch(Exception e) {
            throw new BadRequestException("Gearbox type with requested name already exists.");
        }

        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.CREATED);
    }

    /*
    * Update existing gearbox type, if possible.
    */
    public ResponseEntity<?> updateGearboxType(GearboxTypeDTO gbtDTO, Long id) {
        GearboxType gbt = findOne(id);

        // data validation
        if (!validatePostingData(gbtDTO)) {
            throw new BadRequestException("Invalid format of gearbox type name. Please try again with valid input.");
        }

        // data sanitization
        gbtDTO.setName(Encode.forHtml(gbtDTO.getName()));

        gbt.setName(gbtDTO.getName());
        gbt = save(gbt);

        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.OK);
    }

    /*
    * Deletes gearbox type, if possible.
    */
    public ResponseEntity<?> deleteGearboxType(Long id) {
        GearboxType gbt = findOne(id);
        gbt.setDeleted(true);
        gbt = save(gbt);

        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.OK);
    }

    /*
    * Returns TRUE if gearbox posting data is valid, else returns FALSE.
    */
    private Boolean validatePostingData(GearboxTypeDTO gbtDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9\\-\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        return gbtDTO.getName() != null && !gbtDTO.getName().trim().equals("") &&
                pattern.matcher(gbtDTO.getName().trim()).matches();
    }

}
