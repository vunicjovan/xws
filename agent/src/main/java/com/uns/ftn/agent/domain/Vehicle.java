package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Vehicle.java
 * Author:  Vunic
 * Purpose: Defines the Class Vehicle
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Vehicle {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "km_traveled", nullable = false)
   private double kilometersTraveled;

   @Column(name = "child_seats", nullable = false)
   private int childSeatNumber;

   @Column(name = "android", nullable = false)
   private Boolean hasAndroid = false;

   @OneToOne(mappedBy = "vehicle")
   private Advertisement advertisement;

   @JsonIgnore
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private FuelType fuelType;

   @JsonIgnore
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private GearboxType gearboxType;

   @JsonIgnore
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private VehicleClass vehicleClass;

   @JsonIgnore
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Model model;

}