package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.PriceListItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceListItemDTO {
    private Long id;
    private double dailyPrice;
    private double cdwPrice;
    private double debtPrice;
    private Long creatorId;

    public PriceListItemDTO(PriceListItem priceListItem) {
        this.id = priceListItem.getId();
        this.dailyPrice = priceListItem.getDailyPrice();
        this.cdwPrice = priceListItem.getCdwPrice();
        this.debtPrice = priceListItem.getDebtPrice();
    }
}
