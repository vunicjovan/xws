package com.uns.ftn.agentservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.agentservice.dto.AndroidLocationDTO;
import com.uns.ftn.agentservice.service.AndroidService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    @Autowired
    private AndroidService androidService;

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

}

