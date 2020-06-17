package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;
    private Long modelId;
    private Long vehicleClassId;
    private Long fuelTypeId;
    private Long gearboxTypeId;
    private int kilometersTraveled;
    private int childSeatNumber;
    private Boolean hasAndroid;

    public VehicleDTO(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.modelId = vehicle.getModel().getId();
        this.vehicleClassId = vehicle.getVehicleClass().getId();
        this.fuelTypeId = vehicle.getFuelType().getId();
        this.gearboxTypeId = vehicle.getGearboxType().getId();
        this.kilometersTraveled = vehicle.getKilometersTraveled();
        this.childSeatNumber = vehicle.getChildSeatNumber();
        this.hasAndroid = vehicle.getHasAndroid();
    }

}
