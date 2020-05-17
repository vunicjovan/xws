package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Advertisement.java
 * Author:  Jovan
 * Purpose: Defines the Class Advertisement
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Advertisement {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "price", nullable = false)
   private double price;

   @Column(name = "km_limit", nullable = false)
   private double kilometersPerDayLimit = -1;

   @Column(name = "cdw", nullable = false)
   private Boolean collisionDamageWaiver = false;

   @Column(name = "rating", nullable = false)
   private double rating = 0;
   
   private Set<RentingRequest> rentingRequests;
   private Set<RentingInterval> rentingIntervals;
   private Vehicle vehicle;
   private Set<Cart> carts;
   private Agent owner;
   private Set<Comment> comments;
   private Set<Photo> photos;

}