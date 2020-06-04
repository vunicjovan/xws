package com.uns.ftn.viewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String owner;
    private String location;
    private double price;
    private int kmTraveled;
    private boolean cdw;
    private int dailyLimit;
    private int childSeatNumber;
    private boolean android;
    private String description;
    private Set<String> photos;

}
