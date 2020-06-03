package com.uns.ftn.catalogservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.VehicleClass;
import com.uns.ftn.catalogservice.dto.VehicleClassDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.VehicleClassRepository;
import org.owasp.encoder.Encode;
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

    private VehicleClassRepository vehicleClassRepository;
    private QueueProducer queueProducer;

    @Autowired
    public VehicleClassService(VehicleClassRepository vehicleClassRepository, QueueProducer queueProducer) {
        this.vehicleClassRepository = vehicleClassRepository;
        this.queueProducer = queueProducer;
    }

    public VehicleClass save(VehicleClass vehicleClass) { return vehicleClassRepository.save(vehicleClass); }

    public VehicleClass findOne(Long id) {
        return vehicleClassRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Requested vehicle class does not exist."));
    }

    public List<VehicleClass> findAll() { return vehicleClassRepository.findAll(); }

    public VehicleClass findOne(String name) { return vehicleClassRepository.findByName(name); }

    public Set<VehicleClassDTO> getAllVehicleClasses() {
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAllByDeleted(false);

        return vehicleClassList
                .stream()
                .map(vehicleClass -> new VehicleClassDTO(vehicleClass.getId(), vehicleClass.getName()))
                .collect(Collectors.toSet());
    }

    public ResponseEntity<?> newVehicleClass(VehicleClassDTO vehicleClassDTO) {

        if(!validatePostingData(vehicleClassDTO)) {
            throw new BadRequestException("Invalid format of vehicle class name. Please try again with valid input.");
        }

        vehicleClassDTO.setName(Encode.forHtml(vehicleClassDTO.getName()));

        VehicleClass vehicleClass = new VehicleClass();
        vehicleClass.setName(vehicleClassDTO.getName());

        try {
            vehicleClass = save(vehicleClass);
            queueProducer.produceVehicleClass(new VehicleClassDTO(vehicleClass));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new BadRequestException("Vehicle class with requested name already exist.");
        }

        return new ResponseEntity<>(new VehicleClassDTO(vehicleClass.getId(), vehicleClass.getName()),
                HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateVehicleClass(VehicleClassDTO vehicleClassDTO) {
        VehicleClass vehicleClass = findOne(vehicleClassDTO.getId());

        if(!validatePostingData(vehicleClassDTO)) {
            throw new BadRequestException("Invalid format of vehicle class name. Please try again with valid input.");
        }

        vehicleClassDTO.setName(Encode.forHtml(vehicleClassDTO.getName()));

        vehicleClass.setName(vehicleClassDTO.getName());
        try {
            vehicleClass = save(vehicleClass);
        } catch (Exception e) {
            throw new BadRequestException("Vehicle class with requested name already exist.");
        }

        return new ResponseEntity<>(new VehicleClassDTO(vehicleClass.getId(), vehicleClass.getName()),
                HttpStatus.OK);
    }

    public ResponseEntity<?> deleteVehicleClass(Long id) {
        VehicleClass vehicleClass = findOne(id);
        vehicleClass.setDeleted(true);
        vehicleClass = save(vehicleClass);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Boolean validatePostingData(VehicleClassDTO vehicleClassDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9\\-\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        return vehicleClassDTO.getName() != null
                && !vehicleClassDTO.getName().trim().equals("")
                && pattern.matcher(vehicleClassDTO.getName().trim()).matches();
    }


}