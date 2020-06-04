package com.uns.ftn.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uns.ftn.searchservice.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleDTO {
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
        this.fuelTypeId = vehicle.getFuelType().getId();
        this.gearboxTypeId = vehicle.getGearboxType().getId();
        this.vehicleClassId = vehicle.getVehicleClass().getId();
        this.modelId = vehicle.getModel().getId();
    }
}
