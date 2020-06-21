package com.uns.ftn.rentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertClientResponseDTO {
    private Long id;
    private String model;
    private String brand;
    private String location;
    private double price;
    private Set<User> ratedByUsers;
}
