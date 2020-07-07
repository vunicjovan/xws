package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.Advertisement;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO implements Serializable {

    private Long id;
    private String location;
    private double price;
    private int kilometersPerDayLimit = -1;
    private Boolean collisionDamageWaiver = false;
    private Boolean deleted = false;
    private String description;
    private VehicleDTO vehicle;
    private Long ownerId;
    private double rating = 0;
    private Long priceListItemId;

    public AdvertisementDTO(Advertisement ad) {
        this.id = ad.getId();
        this.ownerId = ad.getOwnerId();
    }

}
