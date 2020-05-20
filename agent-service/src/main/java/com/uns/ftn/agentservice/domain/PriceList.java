package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  PriceList.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceList
 ***********************************************************************/

import lombok.Data;

import java.util.*;

@Data
public class PriceList {

    private Date validFrom;
    private Set<PriceListItem> priceListItem;

}