package com.uns.ftn.searchservice.service;

import com.netflix.discovery.converters.Auto;
import com.uns.ftn.searchservice.domain.FuelType;
import com.uns.ftn.searchservice.domain.GearboxType;
import com.uns.ftn.searchservice.dto.FuelTypeDTO;
import com.uns.ftn.searchservice.dto.GearboxTypeDTO;
import com.uns.ftn.searchservice.repository.FuelTypeRepository;
import com.uns.ftn.searchservice.repository.GearboxTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    private FuelTypeRepository fuelTypeRepository;
    private GearboxTypeRepository gearboxTypeRepository;

    @Autowired
    public CatalogService(FuelTypeRepository fuelTypeRepository, GearboxTypeRepository gearboxTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.gearboxTypeRepository = gearboxTypeRepository;
    }

    public void updateFuelType(FuelTypeDTO fuelTypeDTO) {
        FuelType fuelType = new FuelType();

        fuelType.setId(fuelTypeDTO.getId());
        fuelType.setName(fuelTypeDTO.getName());
        fuelType.setDeleted(fuelTypeDTO.getDeleted());

        fuelTypeRepository.save(fuelType);
    }

    public void updateGearboxType(GearboxTypeDTO gearboxTypeDTO) {
        GearboxType gearboxType = new GearboxType();

        gearboxType.setId(gearboxTypeDTO.getId());
        gearboxType.setName(gearboxTypeDTO.getName());
        gearboxType.setDeleted(gearboxTypeDTO.getDeleted());

        gearboxTypeRepository.save(gearboxType);
    }
}
