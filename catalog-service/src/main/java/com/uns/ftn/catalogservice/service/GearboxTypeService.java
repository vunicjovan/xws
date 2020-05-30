package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.domain.GearboxType;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import com.uns.ftn.catalogservice.repository.GearboxTypeRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class GearboxTypeService {

    @Autowired
    private GearboxTypeRepository gearboxRepo;

    public GearboxType save(GearboxType gbt) {
        return gearboxRepo.save(gbt);
    }

    public GearboxType findOne(Long id) {
        return gearboxRepo.findById(id).orElseGet(null);
    }

    public List<GearboxType> findAll() {
        return gearboxRepo.findAll();
    }

    /*
    * Adds new gearbox type to catalog, if possible.
    */
    public ResponseEntity<?> newGearboxType(GearboxTypeDTO gbtDTO) {
        // data validation
        if (!validatePostingData(gbtDTO)) {
            return new ResponseEntity<>("Invalid format of gearbox type name. Please try again with valid input.",
                    HttpStatus.BAD_REQUEST);
        }

        // data sanitization
        gbtDTO.setName(Encode.forHtml(gbtDTO.getName()));

        GearboxType gbt = new GearboxType();
        gbt.setName(gbtDTO.getName());

        try {
            gbt = save(gbt);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Gearbox type with requested name already exists.",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.CREATED);
    }

    /*
    * Update existing gearbox type, if possible.
    */
    public ResponseEntity<?> updateGearboxType(GearboxTypeDTO gbtDTO, Long id) {
        GearboxType gbt = findOne(id);

        if (gbt == null) {
            return new ResponseEntity<>("Requested gearbox type does not exist.", HttpStatus.NOT_FOUND);
        }

        // data validation
        if (!validatePostingData(gbtDTO)) {
            return new ResponseEntity<>("Invalid format of gearbox type name. Please try again with valid input.",
                    HttpStatus.BAD_REQUEST);
        }

        // data sanitization
        gbtDTO.setName(Encode.forHtml(gbtDTO.getName()));

        gbt.setName(gbtDTO.getName());
        gbt = save(gbt);

        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.CREATED);
    }

    /*
    * Deletes gearbox type, if possible.
    */
    public ResponseEntity<?> deleteGearboxType(Long id) {
        GearboxType gbt = findOne(id);
        if (gbt == null) {
            return new ResponseEntity<>("Requested gearbox type does not exist.", HttpStatus.NOT_FOUND);
        }

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
