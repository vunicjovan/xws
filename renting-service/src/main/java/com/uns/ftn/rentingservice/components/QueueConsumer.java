package com.uns.ftn.rentingservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.rentingservice.controller.CartController;
import com.uns.ftn.rentingservice.dto.AdvertisementDTO;
import com.uns.ftn.rentingservice.service.DataPumpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConsumer.class);

    private DataPumpService dataPumpService;

    @Autowired
    public QueueConsumer(DataPumpService dataPumpService) {
        this.dataPumpService = dataPumpService;
    }


    @RabbitListener(queues = "queue-renting")
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
        }

    }


}

