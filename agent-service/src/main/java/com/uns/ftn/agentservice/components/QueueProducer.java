package com.uns.ftn.agentservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uns.ftn.agentservice.conf.RabbitMQConfiguration;
import com.uns.ftn.agentservice.dto.*;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    @Value("${fanout.exchange}")
    private String fanoutExchangeName;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(AdvertisementDTO advertisementDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(advertisementDTO);
    }

    public void producePhoto(PhotoDTO photoDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(photoDTO);
    }

    public void produceComment(CommDTO commDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(commDTO);
    }

    public void produceUser(UserDTO userDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(userDTO);
    }

    public void produceRentingInterval(RentingIntervalDTO rentingIntervalDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(rentingIntervalDTO);
    }

    public void producePublishedAdCount(AdCountDTO adCountDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(adCountDTO);
    }
}
