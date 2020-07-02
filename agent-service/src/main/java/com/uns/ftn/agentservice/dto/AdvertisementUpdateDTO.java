package com.uns.ftn.agentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementUpdateDTO {

    private Long priceListItemId;
    private String description;

}
