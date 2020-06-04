package com.uns.ftn.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uns.ftn.searchservice.domain.Advertisement;
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
    private int kilometersPerDayLimit = -1;

    @JsonProperty("collisionDamageWaiver")
    private Boolean collisionDamageWaiver = false;

    private String location;

    @JsonProperty("description")
    private String description;

    @JsonProperty("vehicle")
    private VehicleDTO vehicle;

    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("rating")
    private double rating = 0;

    public AdvertisementDTO(Advertisement ad) {
        this.id = ad.getId();
        this.price = ad.getPrice();
        this.kilometersPerDayLimit = ad.getKilometersPerDayLimit();
        this.collisionDamageWaiver = ad.getCollisionDamageWaiver();
        this.location = ad.getLocation();
        this.description = ad.getDescription();
        this.vehicle = new VehicleDTO(ad.getVehicle());
        this.ownerId = ad.getOwnerId();
        this.rating = ad.getRating();
    }
}
