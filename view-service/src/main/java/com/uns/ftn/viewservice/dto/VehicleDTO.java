package com.uns.ftn.viewservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("kilometersTraveled")
    private double kilometersTraveled;

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
}
