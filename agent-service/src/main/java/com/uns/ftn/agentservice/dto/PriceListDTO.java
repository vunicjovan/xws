package com.uns.ftn.agentservice.dto;

import com.uns.ftn.agentservice.domain.PriceList;
import com.uns.ftn.agentservice.domain.PriceListItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriceListDTO {

    private double discount;
    private List<PriceListItemDTO> priceListItems = new ArrayList<>();

    public PriceListDTO(PriceList priceList) {
        this.discount = priceList.getDiscount();
        this.priceListItems = priceList.getPriceListItem().stream().map(PriceListItemDTO::new)
                .collect(Collectors.toList());
    }
}
