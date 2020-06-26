package com.uns.ftn.catalogservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.client.VehicleClient;
import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.VehicleClass;
import com.uns.ftn.catalogservice.dto.VehicleClassDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.VehicleClassRepository;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class VehicleClassService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleClassService.class);

    private VehicleClassRepository vehicleClassRepository;
    private QueueProducer queueProducer;
    private VehicleClient vehicleClient;

    @Autowired
    public VehicleClassService(VehicleClassRepository vehicleClassRepository, QueueProducer queueProducer,
                               VehicleClient vehicleClient) {
        this.vehicleClassRepository = vehicleClassRepository;
        this.queueProducer = queueProducer;
        this.vehicleClient = vehicleClient;
    }

    public VehicleClass save(VehicleClass vehicleClass) { return vehicleClassRepository.save(vehicleClass); }

    public VehicleClass findOne(Long id) {
        return vehicleClassRepository.findById(id).orElseThrow(() ->{
            LOGGER.warn("Database query: unable to find vehicle class with id={}", id);
            return new NotFoundException("Requested vehicle class does not exist.");
        });
    }

    public Set<VehicleClassDTO> findAll() {
        return vehicleClassRepository.findAll().stream().map(VehicleClassDTO::new).collect(Collectors.toSet());
    }

    public VehicleClass findByName(String name) {
        return vehicleClassRepository.findByName(name);
    }

    public Set<VehicleClassDTO> getAllVehicleClasses() {
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAllByDeleted(false);

        return vehicleClassList
                .stream()
                .map(vehicleClass -> new VehicleClassDTO(vehicleClass.getId(), vehicleClass.getName()))
                .collect(Collectors.toSet());
    }

    public ResponseEntity<?> newVehicleClass(VehicleClassDTO vehicleClassDTO) {
        LOGGER.debug("Adding new vehicle class...");
        if (!validatePostingData(vehicleClassDTO)) {
            LOGGER.warn("Invalid format for vehicle class name={}", vehicleClassDTO.getName());
            throw new BadRequestException("Invalid format of vehicle class name. Please try again with valid input.");
        }

        vehicleClassDTO.setName(Encode.forHtml(vehicleClassDTO.getName()));

        if (findByName(vehicleClassDTO.getName()) != null) {
            if (findByName(vehicleClassDTO.getName()).getDeleted()) {
                VehicleClass vc = findByName(vehicleClassDTO.getName());
                LOGGER.warn("Restoring previously deleted brand[id={}, name={}]", vc.getId(), vc.getName());
                vc.setDeleted(false);
                save(vc);
                return new ResponseEntity<>(new VehicleClassDTO(vc), HttpStatus.CREATED);
            }
        }

        VehicleClass vehicleClass = new VehicleClass();
        vehicleClass.setName(vehicleClassDTO.getName());

        try {
            vehicleClass = save(vehicleClass);
            LOGGER.info("Database entry: created new vehicleClass[id={}, name={}]", vehicleClass.getId(),
                    vehicleClass.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving new vehicle class", e);
            throw new BadRequestException("Vehicle class with requested name already exist.");
        }

        queueProducer.produceVehicleClass(new VehicleClassDTO(vehicleClass));

        LOGGER.debug("Finished adding new vehicle class...");
        return new ResponseEntity<>(new VehicleClassDTO(vehicleClass.getId(), vehicleClass.getName()),
                HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateVehicleClass(VehicleClassDTO vehicleClassDTO) {
        LOGGER.debug("Updating vehicleClass[id={}, name={}]", vehicleClassDTO.getId(), vehicleClassDTO.getName());
        VehicleClass vehicleClass = findOne(vehicleClassDTO.getId());

        if(!validatePostingData(vehicleClassDTO)) {
            LOGGER.warn("Invalid format for vehicle class name={}", vehicleClassDTO.getName());
            throw new BadRequestException("Invalid format of vehicle class name. Please try again with valid input.");
        }

        vehicleClassDTO.setName(Encode.forHtml(vehicleClassDTO.getName()));
        vehicleClass.setName(vehicleClassDTO.getName());

        try {
            vehicleClass = save(vehicleClass);
            LOGGER.info("Database update: updated vehicleClass[id={}, name={}]", vehicleClass.getId(),
                    vehicleClass.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving updated vehicle class", e);
            throw new BadRequestException("Vehicle class with requested name already exist.");
        }

        queueProducer.produceVehicleClass(new VehicleClassDTO(vehicleClass));

        LOGGER.debug("Finished updating vehicleClass[id={}, name{}]", vehicleClass.getId(), vehicleClass.getName());
        return new ResponseEntity<>(new VehicleClassDTO(vehicleClass.getId(), vehicleClass.getName()),
                HttpStatus.OK);
    }

    public ResponseEntity<?> deleteVehicleClass(Long id) {
        VehicleClass vehicleClass = findOne(id);

        LOGGER.debug("Deleting vehicleClass[id={}, name={}]", vehicleClass.getId(), vehicleClass.getName());

        if (!this.vehicleClient.checkIfClassIsTaken(id)) {
            LOGGER.warn("Unable to delete vehicleClass[id={}, name={}] because it is still used by some vehicles",
                    vehicleClass.getId(), vehicleClass.getName());
            throw new BadRequestException("Class cannot be deleted because it is still used by some vehicles.");
        }

        vehicleClass.setDeleted(true);
        vehicleClass = save(vehicleClass);
        LOGGER.info("Database entry: deleted vehicleClass[id={}, name={}]", vehicleClass.getId(),
                vehicleClass.getName());

        queueProducer.produceVehicleClass(new VehicleClassDTO(vehicleClass));

        LOGGER.debug("Finished deleting vehicleClass[id={}, name={}]", vehicleClass.getId(), vehicleClass.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Boolean validatePostingData(VehicleClassDTO vehicleClassDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([A-Z])+([a-zA-Z0-9\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        return vehicleClassDTO.getName() != null
                && !vehicleClassDTO.getName().trim().equals("")
                && pattern.matcher(vehicleClassDTO.getName().trim()).matches();
    }


}
