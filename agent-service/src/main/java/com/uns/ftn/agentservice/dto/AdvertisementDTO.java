package com.uns.ftn.agentservice.dto;

import com.uns.ftn.agentservice.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

    private double price;
    private double kilometersPerDayLimit = -1;
    private Boolean collisionDamageWaiver = false;
    private String description;
    private Vehicle vehicle;
    private Long ownerId;
    private Set<Photo> photos;
    private double rating = 0;
    private Set<RentingInterval> rentingIntervals;
    private Set<Comment> comments;

    public AdvertisementDTO(Advertisement ad) {
        this.price = ad.getPrice();
        this.kilometersPerDayLimit = ad.getKilometersPerDayLimit();
        this.collisionDamageWaiver = ad.getCollisionDamageWaiver();
        this.description = ad.getDescription();
        this.vehicle = ad.getVehicle();
        this.ownerId = ad.getOwnerId();
        this.photos = ad.getPhotos();
        this.rating = ad.getRating();
        this.rentingIntervals = ad.getRentingIntervals();
        this.comments = ad.getComments();
    }

}
