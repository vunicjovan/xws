package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.CatalogClient;
import com.uns.ftn.agent.domain.*;
import com.uns.ftn.agent.dto.*;
import com.uns.ftn.agent.exceptions.NotFoundException;
import com.uns.ftn.agent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.GetCatalogResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private ModelRepository modelRepository;
    private BrandRepository brandRepository;
    private FuelTypeRepository fuelTypeRepository;
    private GearboxTypeRepository gearboxTypeRepository;
    private VehicleClassRepository vehicleClassRepository;
    private CatalogClient catalogClient;

    @Autowired
    public CatalogService(
            ModelRepository modelRepository,
            BrandRepository brandRepository,
            FuelTypeRepository fuelTypeRepository,
            GearboxTypeRepository gearboxTypeRepository,
            VehicleClassRepository vehicleClassRepository,
            CatalogClient catalogClient
    ) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.gearboxTypeRepository = gearboxTypeRepository;
        this.vehicleClassRepository = vehicleClassRepository;
        this.catalogClient = catalogClient;
    }

    public Model findOneModel(Long id) {
        return modelRepository.findById(id).orElseThrow(() -> new NotFoundException("Requested model doesn't exist."));
    }

    public Brand findOneBrand(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Requested brand doesn't exist."));
    }

    public FuelType findOneFuelType(Long id) {
        return fuelTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested fuel type doesn't exist."));
    }

    public GearboxType findOneGearboxType(Long id) {
        return gearboxTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested gearbox type doesn't exist."));
    }

    public VehicleClass findOneVehicleClass(Long id) {
        return vehicleClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested vehicle class doesn't exist."));
    }

    public ResponseEntity<?> getCatalog() {
        CatalogDTO catalog = new CatalogDTO();

        try{
        GetCatalogResponse response = catalogClient.getCatalog();

        if(response != null) {
            updateCatalog(response);
        }
        } catch (Exception e) {
            System.out.println("Nema ne radi katalog service");
        }
        catalog.setBrands(brandRepository.findAllByDeleted(false).stream().map(BrandDTO::new).collect(Collectors.toSet()));
        catalog.setFuelTypes(fuelTypeRepository.findAllByDeleted(false).stream().map(FuelTypeDTO::new).collect(Collectors.toSet()));
        catalog.setGearboxTypes(gearboxTypeRepository.findAllByDeleted(false).stream().map(GearboxTypeDTO::new)
                .collect(Collectors.toSet()));
        catalog.setVehicleClasses(vehicleClassRepository.findAllByDeleted(false).stream().map(VehicleClassDTO::new)
                .collect(Collectors.toSet()));
        catalog.setModels(modelRepository.findAllByDeleted(false).stream().map(ModelDTO::new).collect(Collectors.toSet()));

        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private void updateCatalog(GetCatalogResponse catalogDTO) {

        List<Model> models = catalogDTO.getModels().stream().map(modelDTO -> {
            Model model = new Model();
            Brand brand = findOneBrand(modelDTO.getBrand().getId());
            model.setId(modelDTO.getId());
            model.setName(modelDTO.getName());
            model.setBrand(brand);
            model.setDeleted(modelDTO.isDeleted());
            return model;
        }).collect(Collectors.toList());
        modelRepository.saveAll(models);

        List<Brand> brands = catalogDTO.getBrands().stream().map(brandDTO -> {
            Brand brand = new Brand();
            brand.setId(brandDTO.getId());
            brand.setName(brandDTO.getName());
            brand.setDeleted(brandDTO.isDeleted());
            return brand;
        }).collect(Collectors.toList());
        brandRepository.saveAll(brands);



        List<FuelType> fuelTypes = catalogDTO.getFuelTypes().stream().map(fuelTypeDTO -> {
            FuelType fuelType = new FuelType();
            fuelType.setId(fuelTypeDTO.getId());
            fuelType.setName(fuelTypeDTO.getName());
            fuelType.setDeleted(fuelTypeDTO.isDeleted());
            return fuelType;
        }).collect(Collectors.toList());
        fuelTypeRepository.saveAll(fuelTypes);

        List<GearboxType> gearboxTypes = catalogDTO.getGearboxTypes().stream().map(gearboxTypeDTO -> {
            GearboxType gearboxType = new GearboxType();
            gearboxType.setId(gearboxTypeDTO.getId());
            gearboxType.setName(gearboxTypeDTO.getName());
            gearboxType.setDeleted(gearboxTypeDTO.isDeleted());
            return gearboxType;
        }).collect(Collectors.toList());
        gearboxTypeRepository.saveAll(gearboxTypes);

        List<VehicleClass> vehicleClasses = catalogDTO.getVehicleClasses().stream().map(vehicleClassDTO -> {
            VehicleClass vehicleClass = new VehicleClass();
            vehicleClass.setId(vehicleClassDTO.getId());
            vehicleClass.setName(vehicleClassDTO.getName());
            vehicleClass.setDeleted(vehicleClassDTO.isDeleted());
            return vehicleClass;
        }).collect(Collectors.toList());
        vehicleClassRepository.saveAll(vehicleClasses);
    }

}
