package com.uns.ftn.viewservice.dto;

import com.uns.ftn.viewservice.domain.Advertisement;
import com.uns.ftn.viewservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {

    private Long id;
    private String location;
    private double price;
    private int kilometersPerDayLimit = -1;
    private Boolean collisionDamageWaiver = false;
    private String description;
    private VehicleDTO vehicle;
    private Long ownerId;
    private double rating = 0;
    private Set<User> ratedByUsers;

    public AdvertisementDTO(Advertisement ad) {
        this.id = ad.getId();
        this.price = ad.getPrice();
        this.kilometersPerDayLimit = ad.getKilometersPerDayLimit();
        this.collisionDamageWaiver = ad.getCollisionDamageWaiver();
        this.location = ad.getLocation();
        this.description = ad.getDescription();
        this.ownerId = ad.getOwnerId();
        this.rating = ad.getRating();
        this.ratedByUsers = ad.getRatedByUsers();
    }

}
