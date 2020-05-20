package com.uns.ftn.agentservice.domain;

import lombok.Data;

/***********************************************************************
 * Module:  PriceListItem.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceListItem
 ***********************************************************************/

@Data
public class PriceListItem {

    private String name;
    private double dailyPrice;
    private double cdwPrice;
    private double debtPrice;
    private PriceList priceList;

}