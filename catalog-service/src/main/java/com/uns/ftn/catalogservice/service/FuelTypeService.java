package com.uns.ftn.catalogservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.client.VehicleClient;
import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.FuelType;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.FuelTypeRepository;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FuelTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuelTypeService.class);

    private FuelTypeRepository fuelTypeRepository;
    private QueueProducer queueProducer;
    private VehicleClient vehicleClient;

    @Autowired
    public FuelTypeService(FuelTypeRepository fuelTypeRepository, QueueProducer queueProducer, VehicleClient vehicleClient) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.queueProducer = queueProducer;
        this.vehicleClient = vehicleClient;
    }

    public Set<FuelTypeDTO> findAll() {
        return fuelTypeRepository.findAll().stream().map(FuelTypeDTO::new).collect(Collectors.toSet());
    }

    public FuelType findOne(Long id) {
        return fuelTypeRepository.findById(id).orElseThrow(() -> {
            LOGGER.warn("Database query: unable to find fuel type with id={}", id);
            return new NotFoundException("Requested fuel type does not exist.");
        });
    }

    public Set<FuelTypeDTO> getAllFuelTypes() {
        List<FuelType> fuelTypeList = fuelTypeRepository.findAllByDeleted(false);

        return fuelTypeList.stream().sorted(Comparator.comparing(FuelType::getName))
                .map(fuelType -> new FuelTypeDTO(fuelType.getId(), fuelType.getName())).collect(Collectors.toSet());
    }

    public FuelType findByName(String name) {
        return fuelTypeRepository.findByName(name);
    }

    public FuelTypeDTO addFuelType(FuelTypeDTO fuelTypeDTO) {
        LOGGER.debug("Adding new fuel type...");
        fuelTypeDTO.setName(validateAndSanitize(fuelTypeDTO.getName()));

        if (findByName(fuelTypeDTO.getName()) != null) {
            if (findByName(fuelTypeDTO.getName()).getDeleted()) {
                FuelType ft = findByName(fuelTypeDTO.getName());
                LOGGER.warn("Restoring previously deleted fuelType[id={}, name={}]", ft.getId(), ft.getName());
                ft.setDeleted(false);
                fuelTypeRepository.save(ft);
                return new FuelTypeDTO(ft);
            }
        }

        FuelType fuelType;

        try {
            fuelType = fuelTypeRepository.save(new FuelType(fuelTypeDTO.getName()));
            LOGGER.info("Database entry: created new fuelType[id={}, name={}]", fuelType.getId(), fuelType.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving new fuel type", e);
            throw new BadRequestException("Fuel type with that name already exists!");
        }

        queueProducer.produceFuelType(new FuelTypeDTO(fuelType));
        LOGGER.debug("Finished adding new fuelType...");
        return new FuelTypeDTO(fuelType.getId(), fuelType.getName(), fuelType.getDeleted());
    }

    public FuelTypeDTO updateFuelType(Long id, FuelTypeDTO fuelTypeDTO) {
        LOGGER.debug("Updating fuelType[id={}, name={}]", fuelTypeDTO.getId(), fuelTypeDTO.getName());
        fuelTypeDTO.setName(validateAndSanitize(fuelTypeDTO.getName()));

        FuelType fuelType = findOne(id);
        fuelType.setName(fuelTypeDTO.getName());

        try {
            fuelType = fuelTypeRepository.save(fuelType);
            LOGGER.info("Database update: updated fuelType[id={}, name={}]", fuelType.getId(), fuelType.getName());
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving updated fuel type", e);
            throw new BadRequestException("Fuel type with that name already exists!");
        }

        queueProducer.produceFuelType(new FuelTypeDTO(fuelType));

        LOGGER.debug("Finished updating fuelType[id={}, name{}]", fuelType.getId(), fuelType.getName());
        return new FuelTypeDTO(fuelType.getId(), fuelType.getName());
    }

    public FuelTypeDTO deleteFuelType(Long id) {
        FuelType fuelType = findOne(id);
        LOGGER.debug("Deleting fuelType[id={}, name={}]", fuelType.getId(), fuelType.getName());

        if (!vehicleClient.checkIfFuelIsTaken(id)) {
            LOGGER.warn("Unable to delete fuelType[id={}, name={}] because it is still used by some vehicles",
                    fuelType.getId(), fuelType.getName());
            throw new BadRequestException("Fuel type cannot be deleted because it is still used by some vehicles.");
        }

        fuelType.setDeleted(true);
        fuelType = fuelTypeRepository.save(fuelType);
        LOGGER.info("Database entry: deleted fuelType[id={}, name={}]", fuelType.getId(), fuelType.getName());

        queueProducer.produceFuelType(new FuelTypeDTO(fuelType));

        LOGGER.debug("Finished deleting fuelType[id={}, name={}]", fuelType.getId(), fuelType.getName());
        return new FuelTypeDTO(fuelType.getId(), fuelType.getName());
    }

    private String validateAndSanitize(String name) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([A-Z])+([a-zA-Z0-9\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (name == null || name.isEmpty() || !pattern.matcher(name.trim()).matches()) {
            LOGGER.warn("Invalid format for fuel type name={}", name);
            throw new BadRequestException("Given data is not well formed!");
        } else {
            return Encode.forHtml(name);
        }
    }
}
