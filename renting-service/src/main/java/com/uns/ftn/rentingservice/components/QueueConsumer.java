package com.uns.ftn.rentingservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.rentingservice.dto.AdvertisementDTO;
import com.uns.ftn.rentingservice.service.DataPumpService;
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
        }

    }


}

