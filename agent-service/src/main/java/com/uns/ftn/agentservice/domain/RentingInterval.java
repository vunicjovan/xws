package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  RentingInterval.java
 * Author:  Vunic
 * Purpose: Defines the Class RentingInterval
 ***********************************************************************/

import lombok.Data;

import java.util.*;

@Data
public class RentingInterval {

    private Date startDate;
    private Date endDate;
    private Advertisement advertisement;

}