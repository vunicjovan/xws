package com.uns.ftn.catalogservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.FuelType;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.FuelTypeRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    QueueProducer queueProducer;

    public FuelType findOne(Long id) {
        return fuelTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Requested fuel type does not exist."));
    }

    public Set<FuelTypeDTO> getAllFuelTypes() {
        List<FuelType> fuelTypeList = fuelTypeRepository.findAllByDeleted(false);

        return fuelTypeList.stream().sorted(Comparator.comparing(FuelType::getName))
                .map(fuelType -> new FuelTypeDTO(fuelType.getId(), fuelType.getName())).collect(Collectors.toSet());
    }

    public FuelTypeDTO addFuelType(FuelTypeDTO fuelTypeDTO) {

        fuelTypeDTO.setName(validateAndSanitize(fuelTypeDTO.getName()));

        FuelType fuelType;
        try {
            fuelType = fuelTypeRepository.save(new FuelType(fuelTypeDTO.getName()));
        } catch (Exception e) {
            throw new BadRequestException("Fuel type with that name already exists!");
        }

        try {
            queueProducer.produceFuelType(new FuelTypeDTO(fuelType));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new FuelTypeDTO(fuelType.getId(), fuelType.getName());
    }

    public FuelTypeDTO updateFuelType(Long id, FuelTypeDTO fuelTypeDTO) {
        fuelTypeDTO.setName(validateAndSanitize(fuelTypeDTO.getName()));

        FuelType fuelType = fuelTypeRepository.findById(id)
                                    .orElseThrow(() -> new NotFoundException("Fuel type with that id does not exist!"));

        fuelType.setName(fuelTypeDTO.getName());

        try {
            fuelType = fuelTypeRepository.save(fuelType);
        } catch (Exception e) {
            throw new BadRequestException("Fuel type with that name already exists!");
        }

        return new FuelTypeDTO(fuelType.getId(), fuelType.getName());
    }

    public FuelTypeDTO deleteFuelType(Long id) {
        FuelType fuelType = fuelTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fuel type with that id does not exist!"));

        fuelType.setDeleted(true);
        fuelType = fuelTypeRepository.save(fuelType);
        return new FuelTypeDTO(fuelType.getId(), fuelType.getName());
    }

    private String validateAndSanitize(String name) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE)([a-zA-Z0-9\\\\!\\\\?\\\\#\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (name == null || name.isEmpty() || !pattern.matcher(name.trim()).matches()) {
            throw new BadRequestException("Given data is not well formed!");
        } else {
            return Encode.forHtml(name);
        }
    }
}
