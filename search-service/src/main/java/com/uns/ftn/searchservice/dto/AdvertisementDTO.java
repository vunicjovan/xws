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
public class AdvertisementDTO {
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
