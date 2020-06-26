package com.uns.ftn.viewservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.viewservice.domain.VehicleClass;
import com.uns.ftn.viewservice.dto.*;
import com.uns.ftn.viewservice.service.DataPumpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConsumer.class);

    private DataPumpService dataPumpService;

    @Autowired
    public QueueConsumer(DataPumpService dataPumpService) {
        this.dataPumpService = dataPumpService;
    }

    @RabbitListener(queues = "queue-name")
    public void handleMessage(Message message) throws JsonProcessingException {
        LOGGER.info("Rabbit mq message consumer received message: {}", message.getBody());
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if (typeId.contains("AdvertisementDTO")) {
            try {
                AdvertisementDTO advertisementDTO = new ObjectMapper().readValue(messageBody, AdvertisementDTO.class);
                dataPumpService.advertisementHandler(advertisementDTO);
                LOGGER.info("Rabbit mq message consumer forwarded advertisement[id={}] to data pump handler",
                        advertisementDTO.getId());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("FuelTypeDTO")) {
            try {
                FuelTypeDTO fuelTypeDTO = new ObjectMapper().readValue(messageBody, FuelTypeDTO.class);
                dataPumpService.fuelTypeHandler(fuelTypeDTO);
                LOGGER.info("Rabbit mq message consumer forwarded fuelType[id={}, name={}] to data pump handler",
                        fuelTypeDTO.getId(), fuelTypeDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("GearboxTypeDTO")) {
            try {
                GearboxTypeDTO gearboxTypeDTO = new ObjectMapper().readValue(messageBody, GearboxTypeDTO.class);
                dataPumpService.gearboxTypeHandler(gearboxTypeDTO);
                LOGGER.info("Rabbit mq message consumer forwarded gearboxType[id={}, name={}] to data pump handler",
                        gearboxTypeDTO.getId(), gearboxTypeDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("VehicleClassDTO")) {
            try {
                VehicleClassDTO vehicleClassDTO = new ObjectMapper().readValue(messageBody, VehicleClassDTO.class);
                dataPumpService.vehicleClassHandler(vehicleClassDTO);
                LOGGER.info("Rabbit mq message consumer forwarded vehicleClass[id={}, name={}] to data pump handler",
                        vehicleClassDTO.getId(), vehicleClassDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("ModelDTO")) {
            try {
                ModelDTO modelDTO = new ObjectMapper().readValue(messageBody, ModelDTO.class);
                dataPumpService.modelHandler(modelDTO);
                LOGGER.info("Rabbit mq message consumer forwarded gearboxType[id={}, name={}, brand={}] to data pump handler",
                        modelDTO.getId(), modelDTO.getName(), modelDTO.getBrand().getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("BrandDTO")) {
            try {
                BrandDTO brandDTO = new ObjectMapper().readValue(messageBody, BrandDTO.class);
                dataPumpService.brandHandler(brandDTO);
                LOGGER.info("Rabbit mq message consumer forwarded brand[id={}, name={}] to data pump handler",
                        brandDTO.getId(), brandDTO.getName());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if (typeId.contains("PhotoDTO")) {
            try {
                PhotoDTO photoDTO = new ObjectMapper().readValue(messageBody, PhotoDTO.class);
                dataPumpService.photoHandler(photoDTO);
                LOGGER.info("Rabbit mq message consumer forwarded photo[id={}, path={}] to data pump handler",
                        photoDTO.getId(), photoDTO.getPath());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if(typeId.contains("CommDTO")) {
            try {
                CommDTO commentDTO = new ObjectMapper().readValue(messageBody, CommDTO.class);
                dataPumpService.commentHandler(commentDTO);
                LOGGER.info("Rabbit mq message consumer forwarded comment[id={}, title={}] to data pump handler",
                        commentDTO.getId(), commentDTO.getTitle());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        } else if(typeId.contains("UserDTO")) {
            try {
                UserDTO userDTO = new ObjectMapper().readValue(messageBody, UserDTO.class);
                dataPumpService.userHandler(userDTO);
                LOGGER.info("Rabbit mq message consumer forwarded user[id={}] to data pump handler",
                        userDTO.getId());
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred parsing rabbit mq message with typeId={}", typeId, e);
            }
        }

    }


}

