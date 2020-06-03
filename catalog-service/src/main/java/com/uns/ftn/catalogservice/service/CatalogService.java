package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.domain.VehicleClass;
import com.uns.ftn.catalogservice.dto.CatalogDTO;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import com.uns.ftn.catalogservice.dto.VehicleClassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CatalogService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private GearboxTypeService gearboxService;

    private VehicleClassService vehicleClassService;

    @Autowired
    public CatalogService(VehicleClassService vehicleClassService) {
        this.vehicleClassService = vehicleClassService;
    }

    public CatalogDTO getCatalog() {
        CatalogDTO catalogDTO = new CatalogDTO();
        Set<FuelTypeDTO> fuelTypeDTOSet = resourceService.getAllFuelTypes();
        Set<GearboxTypeDTO> gearboxTypeDTOSet = gearboxService.getAllGearboxTypes();
        Set<VehicleClassDTO> vehicleClassDTOSet = vehicleClassService.getAllVehicleClasses();


        catalogDTO.setFuelTypes(fuelTypeDTOSet);
        catalogDTO.setGearboxTypes(gearboxTypeDTOSet);
        catalogDTO.setVehicleClasses(vehicleClassDTOSet);

        return catalogDTO;
    }

}
