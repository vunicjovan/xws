package com.uns.ftn.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
