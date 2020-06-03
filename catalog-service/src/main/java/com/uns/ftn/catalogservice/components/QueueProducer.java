package com.uns.ftn.catalogservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
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

    public void produceFuelType(FuelTypeDTO fuelTypeDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(fuelTypeDTO);
    }
}