package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.Advertisement;
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
    private String advertisementName;
    private double rating;
    private int commentNumber;
    private int kmTraveled;

    public StatisticDTO(Advertisement ad, String adName) {
        this.advertisementId = ad.getId();
        this.advertisementName = adName;
        this.rating = ad.getRating();
        this.commentNumber = ad.getComments().size();
        this.kmTraveled = ad.getVehicle().getKilometersTraveled();
    }

}
