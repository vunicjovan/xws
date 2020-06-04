package com.uns.ftn.agentservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("kilometersTraveled")
    private int kilometersTraveled;

    @JsonProperty("childSeatNumber")
    private int childSeatNumber;

    @JsonProperty("hasAndroid")
    private Boolean hasAndroid = false;

    @JsonProperty("fuelTypeId")
    private Long fuelTypeId;

    @JsonProperty("gearboxTypeId")
    private Long gearboxTypeId;

    @JsonProperty("vehicleClassId")
    private Long vehicleClassId;

    @JsonProperty("modelId")
    private Long modelId;

    public VehicleDTO(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.kilometersTraveled = vehicle.getKilometersTraveled();
        this.childSeatNumber = vehicle.getChildSeatNumber();
        this.hasAndroid = vehicle.getHasAndroid();
        this.fuelTypeId = vehicle.getFuelTypeId();
        this.gearboxTypeId = vehicle.getGearboxTypeId();
        this.vehicleClassId = vehicle.getVehicleClassId();
        this.modelId = vehicle.getModelId();
    }
}
