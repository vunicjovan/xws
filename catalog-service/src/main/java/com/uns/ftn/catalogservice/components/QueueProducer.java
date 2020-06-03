package com.uns.ftn.catalogservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.dto.FuelTypeDTO;
import com.uns.ftn.catalogservice.dto.GearboxTypeDTO;
import com.uns.ftn.catalogservice.dto.VehicleClassDTO;
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

    public void produceGearboxType(GearboxTypeDTO gearboxTypeDTO) throws  JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(gearboxTypeDTO);
    }

    public void produceVehicleClass(VehicleClassDTO vehicleClassDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(vehicleClassDTO);
    }
}
