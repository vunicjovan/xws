package com.uns.ftn.viewservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("price")
    private double price;

    @JsonProperty("kilometersPerDayLimit")
    private double kilometersPerDayLimit = -1;

    @JsonProperty("collisionDamageWaiver")
    private Boolean collisionDamageWaiver = false;

    @JsonProperty("description")
    private String description;

    @JsonProperty("vehicle")
    private VehicleDTO vehicle;

    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("rating")
    private double rating = 0;

}
