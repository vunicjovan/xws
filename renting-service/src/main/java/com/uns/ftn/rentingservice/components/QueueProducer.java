package com.uns.ftn.rentingservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.rentingservice.dto.MessageDTO;
import com.uns.ftn.rentingservice.dto.RentingIntervalDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    @Value("${fanout.mail}")
    private String fanoutMailName;

    @Value("${fanout.exchange}")
    private String fanoutExchangeName;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(MessageDTO mdto) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutMailName);
        rabbitTemplate.convertAndSend(mdto);
    }

    public void produceInterval(RentingIntervalDTO intervalDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange("renting-exchange");
        rabbitTemplate.convertAndSend(intervalDTO);
    }

}
