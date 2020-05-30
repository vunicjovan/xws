package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.dto.CatalogDTO;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

}
