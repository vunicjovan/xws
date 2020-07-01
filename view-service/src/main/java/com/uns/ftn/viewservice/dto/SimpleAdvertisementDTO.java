package com.uns.ftn.viewservice.dto;

import com.uns.ftn.viewservice.domain.Advertisement;
import com.uns.ftn.viewservice.domain.Photo;
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
    private double rating;
    private int kmTraveled;
    private Set<String> photo;

    public SimpleAdvertisementDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.price = advertisement.getPrice();
        this.location = advertisement.getLocation();
        this.brand = advertisement.getVehicle().getModel().getBrand().getName();
        this.model = advertisement.getVehicle().getModel().getName();
        this.rating = advertisement.getRating();
        this.kmTraveled = advertisement.getVehicle().getKilometersTraveled();
        this.photo = advertisement.getPhotos().stream().map(Photo::getPath).collect(Collectors.toSet());
    }

}
