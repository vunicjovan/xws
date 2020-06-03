package com.uns.ftn.searchservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.searchservice.domain.GearboxType;
import com.uns.ftn.searchservice.dto.AdvertisementDTO;
import com.uns.ftn.searchservice.dto.FuelTypeDTO;
import com.uns.ftn.searchservice.dto.GearboxTypeDTO;
import com.uns.ftn.searchservice.repository.AdvertisementRepository;
import com.uns.ftn.searchservice.service.AdvertisementService;
import com.uns.ftn.searchservice.service.CatalogService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    private AdvertisementService advertisementService;
    private CatalogService catalogService;

    @Autowired
    public QueueConsumer(AdvertisementService advertisementService, CatalogService catalogService) {
        this.advertisementService = advertisementService;
        this.catalogService = catalogService;
    }


    @RabbitListener(queues = "queue-name1")
    public void handleMessage(Message message) {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if(typeId.contains("AdvertisementDTO")) {
            try {
                AdvertisementDTO advertisementDTO = new ObjectMapper().readValue(messageBody, AdvertisementDTO.class);
                advertisementService.updateAdvertisement(advertisementDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else if (typeId.contains("FuelTypeDTO")) {
            try {
                FuelTypeDTO fuelTypeDTO = new ObjectMapper().readValue(messageBody, FuelTypeDTO.class);
                catalogService.updateFuelType(fuelTypeDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else if (typeId.contains("GearboxTypeDTO")) {
            try {
                GearboxTypeDTO gearboxTypeDTO = new ObjectMapper().readValue(messageBody, GearboxTypeDTO.class);
                catalogService.updateGearboxType(gearboxTypeDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
