package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.domain.FuelType;
import com.uns.ftn.catalogservice.domain.GearboxType;
import com.uns.ftn.catalogservice.dto.CatalogDTO;
import com.uns.ftn.catalogservice.dto.CheckResponseDTO;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CatalogService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private GearboxTypeService gearboxService;

    public CatalogDTO getCatalog() {
        CatalogDTO catalogDTO = new CatalogDTO();
        Set<FuelTypeDTO> fuelTypeDTOSet = resourceService.getAllFuelTypes();
        Set<GearboxTypeDTO> gearboxTypeDTOSet = gearboxService.getAllGearboxTypes();

        catalogDTO.setFuelTypes(fuelTypeDTOSet);
        catalogDTO.setGearboxTypes(gearboxTypeDTOSet);

        return catalogDTO;
    }

    public ResponseEntity<?> checkResources(String resources) {
        String[] parts = resources.split("-");
        CheckResponseDTO crd = new CheckResponseDTO();

        try {
            GearboxType gbt = gearboxService.findOne(Long.parseLong(parts[2]));
            FuelType ft = resourceService.findOne(Long.parseLong(parts[1]));
            // findOne() for vehicle class
            // findOne() for vehicle model
            crd.setAvailable(true);
            crd.setMessage("All good.");
        }
        catch (Exception e) {
            crd.setAvailable(false);
            crd.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

}
