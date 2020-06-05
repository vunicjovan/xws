package com.uns.ftn.viewservice.dto;

import com.uns.ftn.viewservice.domain.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAdvertisementDTO {
    private Long id;
    private double price;
    private String location;
    private String brand;
    private String model;
    private Long ownerId;

    public CartAdvertisementDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.price = advertisement.getPrice();
        this.location = advertisement.getLocation();
        this.brand = advertisement.getVehicle().getModel().getBrand().getName();
        this.model = advertisement.getVehicle().getModel().getName();
        this.ownerId = advertisement.getOwnerId();
    }
}
