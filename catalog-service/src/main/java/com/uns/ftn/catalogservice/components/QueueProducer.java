package com.uns.ftn.catalogservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.dto.*;
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

    public void produceBrand(BrandDTO brandDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(brandDTO);
    }

    public void produceGearboxType(GearboxTypeDTO gearboxTypeDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(gearboxTypeDTO);
    }

    public void produceVehicleClass(VehicleClassDTO vehicleClassDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(vehicleClassDTO);
    }

    public void produceVehicleModel(ModelDTO modelDTO) throws JsonProcessingException {
        rabbitTemplate.setExchange(fanoutExchangeName);
        rabbitTemplate.convertAndSend(modelDTO);
    }
}
