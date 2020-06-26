package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.client.VehicleClient;
import com.uns.ftn.catalogservice.components.QueueProducer;
import com.uns.ftn.catalogservice.domain.Brand;
import com.uns.ftn.catalogservice.domain.Model;
import com.uns.ftn.catalogservice.dto.ModelDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.ModelRepository;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ModelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);

    private ModelRepository modelRepository;
    private BrandService brandService;
    private VehicleClient vehicleClient;
    private QueueProducer queueProducer;

    @Autowired
    public ModelService(ModelRepository modelRepository, BrandService brandService, VehicleClient vehicleClient,
                        QueueProducer queueProducer) {
        this.modelRepository = modelRepository;
        this.brandService = brandService;
        this.vehicleClient = vehicleClient;
        this.queueProducer = queueProducer;
    }

    public Model save(Model model) {
        return modelRepository.save(model);
    }

    public Model findOne(Long id) {
        return modelRepository.findById(id).orElseThrow(() -> {
            LOGGER.warn("Database query: unable to find model with id={}", id);
            return new NotFoundException("Requested vehicle model does not exist.");
        });
    }

    public Set<ModelDTO> findAll() {
        return modelRepository.findAll().stream().map(ModelDTO::new).collect(Collectors.toSet());
    }

    public Model findByName(String name) {
        return modelRepository.findByName(name);
    }

    public Set<ModelDTO> getAllModels() {
        List<Model> modelList = modelRepository.findAllByDeleted(false);

        return modelList
                .stream()
                .map(model -> new ModelDTO(model))
                .collect(Collectors.toSet());
    }

    public ResponseEntity<?> newModel(Long brandId, ModelDTO modelDTO) {
        LOGGER.debug("Adding new model...");
        if (!validatePostingData(modelDTO)) {
            LOGGER.warn("Invalid format for model name={}", modelDTO.getName());
            throw new BadRequestException("Invalid format of model name. Please try again with valid input.");
        }

        modelDTO.setName(Encode.forHtml(modelDTO.getName()));
        Brand brand = brandService.findOne(brandId);

        if (findByName(modelDTO.getName()) != null) {
            if (findByName(modelDTO.getName()).getDeleted()) {
                Model model = findByName(modelDTO.getName());
                LOGGER.warn("Restoring previously deleted model[id={}, name={}, brand={}]", model.getId(),
                        model.getName(), model.getBrand().getName());
                model.setDeleted(false);
                save(model);
                return new ResponseEntity<>(new ModelDTO(model), HttpStatus.CREATED);
            }
        }

        if (modelRepository.findByNameAndBrand(modelDTO.getName(), brand) != null) {
            LOGGER.warn("Skipping creating model[name={}, brand={}], already exist in database.", modelDTO.getName(),
                    brand.getName());
            throw new BadRequestException("Model with requested name for specified brand already exist.");
        }

        Model model = new Model();
        model.setName(modelDTO.getName());
        model.setBrand(brand);
        model = save(model);
        LOGGER.info("Database entry: created new model[id={}, name={}, brand={}]", model.getId(), model.getName(),
                brand.getName());

        queueProducer.produceModel(new ModelDTO(model));

        LOGGER.debug("Finished adding new brand...");
        return new ResponseEntity<>(new ModelDTO(model), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateModel(Long modelId, Long brandId, ModelDTO modelDTO) {
        LOGGER.debug("Updating brand[id={}, name={}]", modelDTO.getId(), modelDTO.getName());
        Brand brand = brandService.findOne(brandId);

        if (!validatePostingData(modelDTO)) {
            LOGGER.warn("Invalid format for model name={}", modelDTO.getName());
            throw new BadRequestException("Invalid format of model name. Please try again with valid input");
        }

        modelDTO.setName(Encode.forHtml(modelDTO.getName()));
        Model model = modelRepository.findByIdAndBrand(modelId, brand);

        if(model == null) {
            LOGGER.warn("Skipping model[id={}, brand={}] doesn't exist in database", modelDTO.getId(), brand.getName());
            throw new BadRequestException("Model with specified brand does not exist.");
        }

        if (modelRepository.findByNameAndBrand(modelDTO.getName(), brand) != null) {
            LOGGER.warn("Skipping updating model[name={}, brand={}], already exist in database.", modelDTO.getName(),
                    brand.getName());
            throw new BadRequestException("Model with requested name for specified brand already exist.");
        }

        model.setName(modelDTO.getName());
        model = save(model);

        queueProducer.produceModel(new ModelDTO(model));

        LOGGER.debug("Finished updating model[id={}, name{}, brand={}]", model.getId(), model.getName(),
                model.getBrand().getName());
        return new ResponseEntity<>(new ModelDTO(model), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteModel(Long id, Long brandId) {
        Brand brand = brandService.findOne(brandId);
        Model model = modelRepository.findByIdAndBrand(id, brand);
        LOGGER.debug("Deleting model[id={}, brand={}]", id, brand.getName());
        if (model == null) {
            LOGGER.warn("Skipping deleting model[id={}, brand={}] doesn't exist in database", id, brand.getName());
            throw new BadRequestException("Model with specified brand does not exist.");
        }

        if (!this.vehicleClient.checkIfModelIsTaken(id)) {
            LOGGER.warn("Unable to delete model[id={}, name={}, brand={}] because it is still used by some vehicles",
                    model.getId(), model.getName(), brand.getName());
            throw new BadRequestException("Model cannot be deleted because it is still used by some vehicles.");
        }
        model.setDeleted(true);
        model = save(model);

        LOGGER.debug("Finished deleting model[id={}, name{}, brand={}]", model.getId(), model.getName(),
                model.getBrand().getName());
        queueProducer.produceModel(new ModelDTO(model));


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Boolean validatePostingData(ModelDTO modelDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([A-Z])+([a-zA-Z0-9\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        return modelDTO.getName() != null
                && !modelDTO.getName().trim().equals("")
                && pattern.matcher(modelDTO.getName().trim()).matches();
    }


}
