package com.uns.ftn.androidservice.components;

import com.uns.ftn.androidservice.dto.AndroidLocationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    @Value("${android.exchange}")
    private String androidExchangeName;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(AndroidLocationDTO locationDTO) {
        try {
            rabbitTemplate.setExchange(androidExchangeName);
            rabbitTemplate.convertAndSend(locationDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
