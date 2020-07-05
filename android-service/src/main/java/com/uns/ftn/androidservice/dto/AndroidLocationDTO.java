package com.uns.ftn.androidservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AndroidLocationDTO {

    private Long id;
    private String token;
    private double latitude;
    private double longitude;

}
