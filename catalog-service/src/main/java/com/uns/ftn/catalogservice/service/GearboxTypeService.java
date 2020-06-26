package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.client.VehicleClient;
import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.GearboxType;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.GearboxTypeRepository;
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
public class GearboxTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GearboxTypeService.class);

    private GearboxTypeRepository gearboxRepo;
    private QueueProducer queueProducer;
    private VehicleClient vehicleClient;

    @Autowired
    public GearboxTypeService(GearboxTypeRepository gearboxRepo, QueueProducer queueProducer, VehicleClient vehicleClient) {
        this.gearboxRepo = gearboxRepo;
        this.queueProducer = queueProducer;
        this.vehicleClient = vehicleClient;
    }

    public GearboxType save(GearboxType gbt) {
        return gearboxRepo.save(gbt);
    }

    public GearboxType findOne(Long id) {
        return gearboxRepo.findById(id).orElseThrow(() -> {
            LOGGER.warn("Database query: unable to find gearbox type with id={}", id);
            return new NotFoundException("Requested gearbox type does not exist.");
        });
    }

    public Set<GearboxTypeDTO> findAll() {
        return gearboxRepo.findAll().stream().map(GearboxTypeDTO::new).collect(Collectors.toSet());
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
        LOGGER.debug("Adding new gearbox type...");
        if (!validatePostingData(gbtDTO)) {
            LOGGER.warn("Invalid format for gearbox type name={}", gbtDTO.getName());
            throw new BadRequestException("Invalid format of gearbox type name. Please try again with valid input.");
        }

        // data sanitization
        gbtDTO.setName(Encode.forHtml(gbtDTO.getName()));

        if (findByName(gbtDTO.getName()) != null) {
            if (findByName(gbtDTO.getName()).getDeleted()) {
                GearboxType gbt = findByName(gbtDTO.getName());
                LOGGER.warn("Restoring previously deleted gearboxType[id={}, name={}]", gbt.getId(), gbt.getName());
                gbt.setDeleted(false);
                save(gbt);
                return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.CREATED);
            }
        }

        GearboxType gbt = new GearboxType();
        gbt.setName(gbtDTO.getName());

        try {
            gbt = save(gbt);
            LOGGER.info("Database entry: created new gearboxType[id={}, name={}]", gbt.getId(), gbt.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving new gearbox type", e);
            throw new BadRequestException("Gearbox type with requested name already exists.");
        }

        queueProducer.produceGearboxType(new GearboxTypeDTO(gbt.getId(), gbt.getName(), gbt.getDeleted()));

        LOGGER.debug("Finished adding new gearbox type...");
        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.CREATED);
    }

    /*
    * Update existing gearbox type, if possible.
    */
    public ResponseEntity<?> updateGearboxType(GearboxTypeDTO gbtDTO, Long id) {
        LOGGER.debug("Updating gearboxType[id={}, name={}]", gbtDTO.getId(), gbtDTO.getName());
        GearboxType gbt = findOne(id);

        // data validation
        if (!validatePostingData(gbtDTO)) {
            LOGGER.warn("Invalid format for gearbox type name={}", gbtDTO.getName());
            throw new BadRequestException("Invalid format of gearbox type name. Please try again with valid input.");
        }

        // data sanitization
        gbtDTO.setName(Encode.forHtml(gbtDTO.getName()));
        gbt.setName(gbtDTO.getName());

        try {
            gbt = save(gbt);
            LOGGER.info("Database update: updated gearboxType[id={}, name={}]", gbt.getId(), gbt.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving new gearbox type", e);
            throw new BadRequestException("Gearbox type with requested name already exists.");
        }

        queueProducer.produceGearboxType(new GearboxTypeDTO(gbt.getId(), gbt.getName(), gbt.getDeleted()));

        LOGGER.debug("Finished updating gearboxType[id={}, name{}]", gbt.getId(), gbt.getName());
        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.OK);
    }

    /*
    * Deletes gearbox type, if possible.
    */
    public ResponseEntity<?> deleteGearboxType(Long id) {
        GearboxType gbt = findOne(id);
        LOGGER.debug("Deleting gearboxType[id={}, name={}]", gbt.getId(), gbt.getName());

        if (!vehicleClient.checkIfGearboxIsTaken(id)) {
            LOGGER.warn("Unable to delete gearboxType[id={}, name={}] because it is still used by some vehicles",
                    gbt.getId(), gbt.getName());
            throw new BadRequestException("Gearbox type cannot be deleted because it is still used by some vehicles.");
        }

        gbt.setDeleted(true);
        gbt = save(gbt);
        LOGGER.info("Database entry: deleted gearboxType[id={}, name={}]", gbt.getId(), gbt.getName());

        queueProducer.produceGearboxType(new GearboxTypeDTO(gbt.getId(), gbt.getName(), gbt.getDeleted()));

        LOGGER.debug("Finished deleting brand[id={}, name={}]", gbt.getId(), gbt.getName());
        return new ResponseEntity<>(new GearboxTypeDTO(gbt), HttpStatus.OK);
    }

    /*
    * Returns TRUE if gearbox posting data is valid, else returns FALSE.
    */
    private Boolean validatePostingData(GearboxTypeDTO gbtDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([A-Z])+([a-zA-Z0-9\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        return gbtDTO.getName() != null && !gbtDTO.getName().trim().equals("") &&
                pattern.matcher(gbtDTO.getName().trim()).matches();
    }

}
