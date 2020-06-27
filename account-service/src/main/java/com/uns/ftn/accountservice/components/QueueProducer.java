package com.uns.ftn.accountservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.accountservice.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueProducer.class);

    @Value("${fanout.exchange}")
    private String fanoutExchangeName;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(MessageDTO mdto) {
        rabbitTemplate.setExchange(fanoutExchangeName);
        try {
            rabbitTemplate.convertAndSend(mdto);
            LOGGER.debug("Verification mail has been sent through message queue");
        } catch (Exception e) {
            LOGGER.error("Error while trying to send email to the queue", e);
        }
    }

}
