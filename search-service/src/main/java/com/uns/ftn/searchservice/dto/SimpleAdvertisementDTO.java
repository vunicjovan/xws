package com.uns.ftn.searchservice.dto;

import com.uns.ftn.searchservice.domain.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleAdvertisementDTO {

    private Long id;
    private double price;
    private String location;
    private String brand;
    private String model;
    private Set<String> photo;

    public SimpleAdvertisementDTO(Advertisement ad) {
        this.id = ad.getId();
        this.price = ad.getPrice();
        this.location = ad.getLocation();
        this.brand = ad.getVehicle().getModel().getBrand().getName();
        this.model = ad.getVehicle().getModel().getName();
        this.photo =  ad.getPhotos().stream().map(photo -> photo.getPath()).collect(Collectors.toSet());
    }
}
