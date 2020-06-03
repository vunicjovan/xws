package com.uns.ftn.viewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleAdvertisementDTO {

    private Long id;
    private double price;
    private String place;
    private String brand;
    private String model;
    private Set<String> photo;

}
