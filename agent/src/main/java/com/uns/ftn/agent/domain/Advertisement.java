package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Advertisement.java
 * Author:  Jovan
 * Purpose: Defines the Class Advertisement
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
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

   @JsonIgnore
   @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<RentingInterval> rentingIntervals;

   @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
   @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
   private Vehicle vehicle;

   @JsonIgnore
   @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
   private Agent owner;

   @JsonIgnore
   @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Photo> photos;

}