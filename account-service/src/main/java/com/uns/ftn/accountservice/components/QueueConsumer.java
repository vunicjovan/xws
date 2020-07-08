package com.uns.ftn.accountservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.accountservice.dto.AdCountDTO;
import com.uns.ftn.accountservice.service.DataPumpService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    @Autowired
    private DataPumpService dataPumpService;

    @RabbitListener(queues = "queue-account")
    public void handleMessage(Message message) throws JsonProcessingException {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("****" + messageBody + "****");

        if (typeId.contains("AdCountDTO")) {
            try {
                AdCountDTO adCountDTO = new ObjectMapper().readValue(messageBody, AdCountDTO.class);
                dataPumpService.adCountHandler(adCountDTO);
            } catch (JsonProcessingException jse) {
                jse.printStackTrace();
            }
        }
    }
}
