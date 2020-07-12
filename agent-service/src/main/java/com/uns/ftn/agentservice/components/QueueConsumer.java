package com.uns.ftn.agentservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.agentservice.dto.AndroidLocationDTO;
import com.uns.ftn.agentservice.dto.RentingIntervalDTO;
import com.uns.ftn.agentservice.service.AndroidService;
import com.uns.ftn.agentservice.service.RentingIntervalService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    @Autowired
    private AndroidService androidService;

    @Autowired
    private RentingIntervalService rentingIntervalService;

    @RabbitListener(queues = "queue-android")
    public void handleMessage(Message message) {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if (typeId.contains("AndroidLocationDTO")) {
            try {
                AndroidLocationDTO adto = new ObjectMapper().readValue(messageBody, AndroidLocationDTO.class);
                androidService.updateLocation(adto);
                System.out.println(adto.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = "queue-renting1")
    public void handleRentingMessage(Message message) {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if (typeId.contains("RentingIntervalDTO")) {
            try {
                RentingIntervalDTO rdto = new ObjectMapper().readValue(messageBody, RentingIntervalDTO.class);
                rentingIntervalService.manuallyAddInterval(rdto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

