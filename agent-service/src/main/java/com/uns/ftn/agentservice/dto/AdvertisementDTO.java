package com.uns.ftn.agentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uns.ftn.agentservice.domain.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("price")
    private double price;

    @JsonProperty("deleted")
    private Boolean deleted = false;

    @JsonProperty("kilometersPerDayLimit")
    private int kilometersPerDayLimit = -1;

    private String location;

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

    private Long priceListItemId;

//    private Set<UserDTO> ratedByUsers;

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
        this.priceListItemId = ad.getPriceListItem().getId();
        this.deleted = ad.getDeleted();
//        this.ratedByUsers = ad.getRatedByUsers().stream().map(UserDTO::new).collect(Collectors.toSet());
    }

    public AdvertisementDTO(rs.ac.uns.ftn.advertisement.Advertisement ad) {
        this.id = ad.getId();
        this.price = ad.getPrice();
        this.kilometersPerDayLimit = ad.getKilometersPerDayLimit();
        this.collisionDamageWaiver = ad.isCollisionDamageWaiver();
        this.location = ad.getLocation();
        this.description = ad.getDescription();
        this.vehicle = new VehicleDTO(ad.getVehicle());
        this.ownerId = ad.getOwnerId();
        this.rating = ad.getRating();
    }

}
