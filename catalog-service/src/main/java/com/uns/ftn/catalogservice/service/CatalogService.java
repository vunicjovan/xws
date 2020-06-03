package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.dto.BrandDTO;
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

    @Autowired
    private BrandService brandService;

    public CatalogDTO getCatalog() {
        CatalogDTO catalogDTO = new CatalogDTO();
        Set<FuelTypeDTO> fuelTypeDTOSet = resourceService.getAllFuelTypes();
        Set<GearboxTypeDTO> gearboxTypeDTOSet = gearboxService.getAllGearboxTypes();
        Set<BrandDTO> brandDTOSet = brandService.getAllBrands();

        catalogDTO.setFuelTypes(fuelTypeDTOSet);
        catalogDTO.setGearboxTypes(gearboxTypeDTOSet);
        catalogDTO.setBrands(brandDTOSet);

        return catalogDTO;
    }

}
