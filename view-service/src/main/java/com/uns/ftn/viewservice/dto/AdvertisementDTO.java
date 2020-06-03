package com.uns.ftn.viewservice.dto;

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
    private double price;
    private int kilometersPerDayLimit = -1;
    private Boolean collisionDamageWaiver = false;
    private String description;
    private VehicleDTO vehicle;
    private Long ownerId;
    private double rating = 0;

}
