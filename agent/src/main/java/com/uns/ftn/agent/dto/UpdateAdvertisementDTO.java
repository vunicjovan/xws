package com.uns.ftn.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdvertisementDTO {
    private Long advertisementId;
    private Long priceListItemId;
    private String description;
}
