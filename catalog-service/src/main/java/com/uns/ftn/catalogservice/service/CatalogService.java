package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.dto.CatalogDTO;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CatalogService {

    @Autowired
    private ResourceService resourceService;

    public CatalogDTO getCatalog() {
        CatalogDTO catalogDTO = new CatalogDTO();
        Set<FuelTypeDTO> fuelTypeDTOSet = resourceService.getAllFuelTypes();
        catalogDTO.setFuelTypeDTOSet(fuelTypeDTOSet);
        return catalogDTO;
    }

}
