package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Vehicle.java
 * Author:  Vunic
 * Purpose: Defines the Class Vehicle
 ***********************************************************************/

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
   
   private Advertisement advertisement;
   private FuelType fuelType;
   private GearboxType gearboxType;
   private VehicleClass vehicleClass;
   private Model model;

}