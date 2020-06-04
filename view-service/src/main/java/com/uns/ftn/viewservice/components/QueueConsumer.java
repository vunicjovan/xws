package com.uns.ftn.viewservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.viewservice.domain.VehicleClass;
import com.uns.ftn.viewservice.dto.*;
import com.uns.ftn.viewservice.service.DataPumpService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    @Autowired
    private DataPumpService dataPumpService;

    @RabbitListener(queues = "queue-name")
    public void handleMessage(Message message) throws JsonProcessingException {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if (typeId.contains("AdvertisementDTO")) {
            try {
                AdvertisementDTO advertisementDTO = new ObjectMapper().readValue(messageBody, AdvertisementDTO.class);
                dataPumpService.advertisementHandler(advertisementDTO);
                System.out.println(advertisementDTO.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (typeId.contains("FuelTypeDTO")) {
            try {
                FuelTypeDTO fuelTypeDTO = new ObjectMapper().readValue(messageBody, FuelTypeDTO.class);
                dataPumpService.fuelTypeHandler(fuelTypeDTO);
                System.out.println(fuelTypeDTO.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (typeId.contains("GearboxTypeDTO")) {
            GearboxTypeDTO gearboxTypeDTO = new ObjectMapper().readValue(messageBody, GearboxTypeDTO.class);
            dataPumpService.gearboxTypeHandler(gearboxTypeDTO);
        } else if (typeId.contains("VehicleClassDTO")) {
            VehicleClassDTO vehicleClassDTO = new ObjectMapper().readValue(messageBody, VehicleClassDTO.class);
            dataPumpService.vehicleClassHandler(vehicleClassDTO);
        } else if (typeId.contains("ModelDTO")) {
            ModelDTO modelDTO = new ObjectMapper().readValue(messageBody, ModelDTO.class);
            dataPumpService.modelHandler(modelDTO);
        } else if (typeId.contains("BrandDTO")) {
            BrandDTO brandDTO = new ObjectMapper().readValue(messageBody, BrandDTO.class);
            dataPumpService.brandHandler(brandDTO);
        } else if (typeId.contains("PhotoDTO")) {
            PhotoDTO photoDTO = new ObjectMapper().readValue(messageBody, PhotoDTO.class);
            dataPumpService.photoHandler(photoDTO);
        }

    }


}

