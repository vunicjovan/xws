package com.uns.ftn.catalogservice.service;

import com.uns.ftn.catalogservice.domain.Brand;
import com.uns.ftn.catalogservice.domain.Model;
import com.uns.ftn.catalogservice.dto.ModelDTO;
import com.uns.ftn.catalogservice.exceptions.BadRequestException;
import com.uns.ftn.catalogservice.exceptions.NotFoundException;
import com.uns.ftn.catalogservice.repository.ModelRepository;
import org.owasp.encoder.Encode;
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
    private ModelRepository modelRepository;
    private BrandService brandService;

    @Autowired
    public ModelService(ModelRepository modelRepository, BrandService brandService) {
        this.modelRepository = modelRepository;
        this.brandService = brandService;
    }

    public Model save(Model model) {
        return modelRepository.save(model);
    }

    public Model findOne(Long id) {
        return modelRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Requested vehicle model does not exist."));
    }

    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    public Model findOne(String name) {
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

        if (!validatePostingData(modelDTO)) {
            throw new BadRequestException("Invalid format of model name. Please try again with valid input.");
        }

        modelDTO.setName(Encode.forHtml(modelDTO.getName()));
        Brand brand = brandService.findOne(brandId);

        if (modelRepository.findByNameAndBrand(modelDTO.getName(), brand) != null) {
            throw new BadRequestException("Model with requested name for specified brand already exist.");
        }

        Model model = new Model();
        model.setName(modelDTO.getName());
        model.setBrand(brand);
        model = save(model);
        return new ResponseEntity<>(new ModelDTO(model), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateModel(Long modelId, Long brandId, ModelDTO modelDTO) {
        Brand brand = brandService.findOne(brandId);

        if (!validatePostingData(modelDTO)) {
            throw new BadRequestException("Invalid format of model name. Please try again with valid input");
        }

        modelDTO.setName(Encode.forHtml(modelDTO.getName()));
        Model model = modelRepository.findByIdAndBrand(modelId, brand);

        if(model == null) {
            throw new BadRequestException("Model with specified brand does not exist.");
        }

        if (modelRepository.findByNameAndBrand(modelDTO.getName(), brand) != null) {
            throw new BadRequestException("Model with requested name for specified brand already exist.");
        }

        model.setName(modelDTO.getName());
        model = save(model);

        return new ResponseEntity<>(new ModelDTO(model), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteModel(Long id, Long brandId) {
        Brand brand = brandService.findOne(brandId);
        Model model = modelRepository.findByIdAndBrand(id, brand);
        if (model == null) {
            throw new BadRequestException("Model with specified brand does not exist.");
        }
        model.setDeleted(true);
        model = save(model);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Boolean validatePostingData(ModelDTO modelDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9\\-\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        return modelDTO.getName() != null
                && !modelDTO.getName().trim().equals("")
                && pattern.matcher(modelDTO.getName().trim()).matches();
    }


}
