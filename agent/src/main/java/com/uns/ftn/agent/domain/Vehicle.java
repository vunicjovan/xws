package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Vehicle.java
 * Author:  Vunic
 * Purpose: Defines the Class Vehicle
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "kilometersTraveled", nullable = false)
   private int kilometersTraveled;

   @Column(name = "childSeatNumber", nullable = false)
   private int childSeatNumber;

   @Column(name = "hasAndroid", nullable = false)
   private Boolean hasAndroid = false;

   @JsonIgnore
   @OneToOne
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