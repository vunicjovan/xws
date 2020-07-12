package com.uns.ftn.rentingservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.rentingservice.domain.RentingInterval;
import com.uns.ftn.rentingservice.dto.AdvertisementDTO;
import com.uns.ftn.rentingservice.dto.RentingIntervalDTO;
import com.uns.ftn.rentingservice.service.DataPumpService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    @Autowired
    private DataPumpService dataPumpService;

    @RabbitListener(queues = "queue-renting")
    public void handleMessage(Message message) throws JsonProcessingException {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("****" + messageBody + "****");
        if (typeId.contains("AdvertisementDTO")) {
            try {
                AdvertisementDTO advertisementDTO = new ObjectMapper().readValue(messageBody, AdvertisementDTO.class);
                dataPumpService.advertisementHandler(advertisementDTO);
                System.out.println(advertisementDTO.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (typeId.contains("RentingIntervalDTO")) {
            try {
                RentingIntervalDTO rentingIntervalDTO =
                        new ObjectMapper().readValue(messageBody, RentingIntervalDTO.class);
                dataPumpService.rentingIntervalHandler(rentingIntervalDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }


}

