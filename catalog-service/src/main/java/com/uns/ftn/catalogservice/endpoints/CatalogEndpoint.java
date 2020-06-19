package com.uns.ftn.catalogservice.endpoints;

import com.uns.ftn.catalogservice.dto.*;
import com.uns.ftn.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.catalog.*;

@Endpoint
public class CatalogEndpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/catalog";

    private CatalogService catalogService;

    @Autowired
    public CatalogEndpoint(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCatalogRequest")
    @ResponsePayload
    public GetCatalogResponse getCatalog() {
        GetCatalogResponse response = new GetCatalogResponse();
        CatalogDTO catalogDTO = catalogService.getCatalogSOAP();

        for(BrandDTO brandDTO : catalogDTO.getBrands()) {
            Brand brand = new Brand();
            brand.setId(brandDTO.getId());
            brand.setName(brandDTO.getName());
            brand.setDeleted(brandDTO.getDeleted());
            response.getBrands().add(brand);
        }

        for(ModelDTO modelDTO: catalogDTO.getModels()) {
            Model model = new Model();
            Brand brand = new Brand();
            brand.setId(modelDTO.getBrand().getId());
            brand.setName(modelDTO.getBrand().getName());
            model.setId(modelDTO.getId());
            model.setName(modelDTO.getName());
            model.setBrand(brand);
            model.setDeleted(modelDTO.getDeleted());
            response.getModels().add(model);
        }

        for(FuelTypeDTO fuelTypeDTO : catalogDTO.getFuelTypes()) {
            FuelType fuelType = new FuelType();
            fuelType.setId(fuelTypeDTO.getId());
            fuelType.setName(fuelTypeDTO.getName());
            fuelType.setDeleted(fuelTypeDTO.getDeleted());
            response.getFuelTypes().add(fuelType);
        }

        for(GearboxTypeDTO gearboxTypeDTO : catalogDTO.getGearboxTypes()) {
            GearboxType gearboxType = new GearboxType();
            gearboxType.setId(gearboxTypeDTO.getId());
            gearboxType.setName(gearboxTypeDTO.getName());
            gearboxType.setDeleted(gearboxTypeDTO.getDeleted());
            response.getGearboxTypes().add(gearboxType);
        }

        for(VehicleClassDTO vehicleClassDTO : catalogDTO.getVehicleClasses()) {
            VehicleClass vehicleClass = new VehicleClass();
            vehicleClass.setId(vehicleClassDTO.getId());
            vehicleClass.setName(vehicleClassDTO.getName());
            vehicleClass.setDeleted(vehicleClassDTO.getDeleted());
            response.getVehicleClasses().add(vehicleClass);
        }

        return response;
    }
}
