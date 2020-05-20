package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  Advertisement.java
 * Author:  Vunic
 * Purpose: Defines the Class Advertisement
 ***********************************************************************/

import lombok.Data;

import java.util.*;

@Data
public class Advertisement {

    private double price;
    private double kilometersPerDayLimit = -1;
    private Boolean collisionDamageWaiver = false;
    private double rating = 0;
    private String description;

    private Set<RentingInterval> rentingIntervals;
    private Vehicle vehicle;
    private Long ownerId;
    private Set<Comment> comments;
    private Set<Photo> photos;

}