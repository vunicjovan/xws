package com.uns.ftn.mailservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.mailservice.dto.MessageDTO;
import com.uns.ftn.mailservice.service.MessagingService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    @Autowired
    private MessagingService messageService;

    @RabbitListener(queues = "queue-mail")
    public void handleMessage(Message message) throws JsonProcessingException {
        String typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        if (typeId.contains("MessageDTO")) {
            try {
                MessageDTO mdto = new ObjectMapper().readValue(messageBody, MessageDTO.class);
                messageService.sendMail(mdto);
                System.out.println(mdto.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

}
