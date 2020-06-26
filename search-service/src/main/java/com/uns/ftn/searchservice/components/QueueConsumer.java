package com.uns.ftn.searchservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.searchservice.domain.GearboxType;
import com.uns.ftn.searchservice.dto.*;
import com.uns.ftn.searchservice.repository.AdvertisementRepository;
import com.uns.ftn.searchservice.service.AdvertisementService;
import com.uns.ftn.searchservice.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConsumer.class);

    private AdvertisementService advertisementService;
    private CatalogService catalogService;

    @Autowired
    public QueueConsumer(AdvertisementService advertisementService,
                         CatalogService catalogService) {
        this.advertisementService = advertisementService;
        this.catalogService = catalogService;
    }


    @RabbitListener(queues = "queue-name1")
    public void handleMessage(Message message) {
        LOGGER.info("Rabbit mq message consumer received message: {}", message.getBody());
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if(typeId.contains("AdvertisementDTO")) {
            try {
                AdvertisementDTO advertisementDTO = new ObjectMapper().readValue(messageBody, AdvertisementDTO.class);
                advertisementService.updateAdvertisement(advertisementDTO);
                LOGGER.info("Rabbit mq message consumer forwarded advertisement[id={}] to data pump handler",
                        advertisementDTO.getId());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        }
        else if (typeId.contains("FuelTypeDTO")) {
            try {
                FuelTypeDTO fuelTypeDTO = new ObjectMapper().readValue(messageBody, FuelTypeDTO.class);
                catalogService.updateFuelType(fuelTypeDTO);
                LOGGER.info("Rabbit mq message consumer forwarded fuelType[id={}, name={}] to data pump handler",
                        fuelTypeDTO.getId(), fuelTypeDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        }
        else if (typeId.contains("GearboxTypeDTO")) {
            try {
                GearboxTypeDTO gearboxTypeDTO = new ObjectMapper().readValue(messageBody, GearboxTypeDTO.class);
                catalogService.updateGearboxType(gearboxTypeDTO);
                LOGGER.info("Rabbit mq message consumer forwarded gearboxType[id={}, name={}] to data pump handler",
                        gearboxTypeDTO.getId(), gearboxTypeDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("BrandDTO")) {
            try {
                BrandDTO brandDTO = new ObjectMapper().readValue(messageBody, BrandDTO.class);
                catalogService.updateBrand(brandDTO);
                LOGGER.info("Rabbit mq message consumer forwarded brand[id={}, name={}] to data pump handler",
                        brandDTO.getId(), brandDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("ModelDTO")) {
            try {
                ModelDTO modelDTO = new ObjectMapper().readValue(messageBody, ModelDTO.class);
                catalogService.updateModel(modelDTO);
                LOGGER.info("Rabbit mq message consumer forwarded gearboxType[id={}, name={}, brand={}] to data pump handler",
                        modelDTO.getId(), modelDTO.getName(), modelDTO.getBrand().getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("VehicleClassDTO")) {
            try {
                VehicleClassDTO vehicleClassDTO = new ObjectMapper().readValue(messageBody, VehicleClassDTO.class);
                catalogService.updateVehicleClass(vehicleClassDTO);
                LOGGER.info("Rabbit mq message consumer forwarded vehicleClass[id={}, name={}] to data pump handler",
                        vehicleClassDTO.getId(), vehicleClassDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("PhotoDTO")) {
            try {
                PhotoDTO photoDTO = new ObjectMapper().readValue(messageBody, PhotoDTO.class);
                advertisementService.updatePhoto(photoDTO);
                LOGGER.info("Rabbit mq message consumer forwarded photo[id={}, path={}] to data pump handler",
                        photoDTO.getId(), photoDTO.getPath());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        }
    }
}
