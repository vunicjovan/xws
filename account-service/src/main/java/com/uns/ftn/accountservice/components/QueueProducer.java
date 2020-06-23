package com.uns.ftn.accountservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.accountservice.dto.MessageDTO;
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

    public void produce(MessageDTO mdto) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(mdto);
    }

}
