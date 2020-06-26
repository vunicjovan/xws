package com.uns.ftn.catalogservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.catalogservice.dto.*;
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

    public void produceFuelType(FuelTypeDTO fuelTypeDTO){
        rabbitTemplate.setExchange(fanoutExchangeName);
        try{
            rabbitTemplate.convertAndSend(fuelTypeDTO);
            LOGGER.info("Published fuelType[id={}, name={}] to exchange={}", fuelTypeDTO.getId(), fuelTypeDTO.getName(),
                    fanoutExchangeName);
        } catch (Exception e) {
            LOGGER.error("Error occurred during publishing fuelType[id={}, name={}]",
                    fuelTypeDTO.getId(), fuelTypeDTO.getName(), e);
        }
    }


    public void produceBrand(BrandDTO brandDTO) {
        rabbitTemplate.setExchange(fanoutExchangeName);
        try {
            rabbitTemplate.convertAndSend(brandDTO);
            LOGGER.info("Published brand[id={}, name={}] to exchange={}", brandDTO.getId(), brandDTO.getName(),
                    fanoutExchangeName);
        } catch (Exception e) {
            LOGGER.error("Error occurred during publishing brand[id={}, name={}]",
                    brandDTO.getId(), brandDTO.getName(), e);
        }

    }

    public void produceGearboxType(GearboxTypeDTO gearboxTypeDTO){
        rabbitTemplate.setExchange(fanoutExchangeName);
        try {
            rabbitTemplate.convertAndSend(gearboxTypeDTO);
            LOGGER.info("Published gearboxType[id={}, name={}] to exchange={}", gearboxTypeDTO.getId(),
                    gearboxTypeDTO.getName(), fanoutExchangeName);
        } catch (Exception e) {
            LOGGER.error("Error occurred during publishing gearboxType[id={}, name={}]",
                    gearboxTypeDTO.getId(), gearboxTypeDTO.getName(), e);
        }
    }

    public void produceVehicleClass(VehicleClassDTO vehicleClassDTO){
        rabbitTemplate.setExchange(fanoutExchangeName);
        try {
            rabbitTemplate.convertAndSend(vehicleClassDTO);
            LOGGER.info("Published vehicleClass[id={}, name={}] to exchange={}", vehicleClassDTO.getId(),
                    vehicleClassDTO.getName(), fanoutExchangeName);
        } catch (Exception e) {
            LOGGER.error("Error occurred during publishing gearboxType[id={}, name={}]",
                    vehicleClassDTO.getId(), vehicleClassDTO.getName(), e);
        }
    }

    public void produceModel(ModelDTO modelDTO) {
        rabbitTemplate.setExchange(fanoutExchangeName);
        try{
            rabbitTemplate.convertAndSend(modelDTO);
            LOGGER.info("Published model[id={}, name={}, brand={}] to exchange={}", modelDTO.getId(),
                    modelDTO.getName(), modelDTO.getBrand().getName(),fanoutExchangeName);
        } catch (Exception e) {
            LOGGER.error("Error occurred during publishing gearboxType[id={}, name={}, brand={}]",
                    modelDTO.getId(), modelDTO.getName(), modelDTO.getBrand().getName(), e);
        }
    }
}
