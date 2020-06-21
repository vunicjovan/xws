package com.uns.ftn.viewservice.dto;

import com.uns.ftn.viewservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Set<UserDTO> ratedByUsers;
}
