package com.uns.ftn.rentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleDTO implements Serializable {

    private Long id;
    private int kilometersTraveled;
    private int childSeatNumber;
    private Boolean hasAndroid = false;
    private Long fuelTypeId;
    private Long gearboxTypeId;
    private Long vehicleClassId;
    private Long modelId;

}
