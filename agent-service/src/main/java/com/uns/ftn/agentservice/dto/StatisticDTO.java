package com.uns.ftn.agentservice.dto;

import com.uns.ftn.agentservice.domain.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class StatisticDTO {

    private Long advertisementId;
    private double rating;
    private int commentNumber;
    private int kmTraveled;

    public StatisticDTO(Advertisement ad) {
        this.advertisementId = ad.getId();
        this.rating = ad.getRating();
        this.commentNumber = ad.getComments().size();
        this.kmTraveled = ad.getVehicle().getKilometersTraveled();
    }

}
