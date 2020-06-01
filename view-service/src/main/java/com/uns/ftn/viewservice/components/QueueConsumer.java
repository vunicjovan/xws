package com.uns.ftn.viewservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.viewservice.dto.AdvertisementDTO;
import com.uns.ftn.viewservice.dto.FuelTypeDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    @RabbitListener(queues = "queue-name")
    public void handleMessage(Message message) {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if(typeId.contains("AdvertisementDTO")) {
            try {
                AdvertisementDTO advertisementDTO = new ObjectMapper().readValue(messageBody, AdvertisementDTO.class);
                System.out.println(advertisementDTO.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else if (typeId.contains("FuelTypeDTO")) {
            try {
                FuelTypeDTO fuelTypeDTO = new ObjectMapper().readValue(messageBody, FuelTypeDTO.class);
                System.out.println(fuelTypeDTO.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }


}

