package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedAdvertisementDTO {
    private Long id;
    private String brand;
    private String model;
    private String vehicleClass;
    private String gearbox;
    private String fuel;
    private String location;
    private double price;
    private int kmTraveled;
    private boolean cdw;
    private int dailyLimit;
    private int childSeatNumber;
    private boolean android;
    private String description;
    private List<CommentDTO> comments;
    private List<String> photos = new ArrayList<>();

    public DetailedAdvertisementDTO(Advertisement advertisement, List<CommentDTO> commentDTOList) {
        this.id = advertisement.getId();
        this.brand = advertisement.getVehicle().getModel().getBrand().getName();
        this.model = advertisement.getVehicle().getModel().getName();
        this.vehicleClass = advertisement.getVehicle().getVehicleClass().getName();
        this.gearbox = advertisement.getVehicle().getGearboxType().getName();
        this.fuel = advertisement.getVehicle().getFuelType().getName();
        this. location = advertisement.getLocation();
        this.price = advertisement.getPrice();
        this. kmTraveled = advertisement.getVehicle().getKilometersTraveled();
        this.cdw = advertisement.getCollisionDamageWaiver();
        this.kmTraveled = advertisement.getKilometersPerDayLimit();
        this.childSeatNumber = advertisement.getVehicle().getChildSeatNumber();
        this.android = advertisement.getVehicle().getHasAndroid();
        this.description = advertisement.getDescription();
        this.comments = commentDTOList;
    }

}
