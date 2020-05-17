package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  AdRequest.java
 * Author:  Jovan
 * Purpose: Defines the Class AdRequest
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class AdRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "fuel", nullable = false)
   private FuelType fuelType;

   @Column(name = "gearbox", nullable = false)
   private GearboxType gearboxType;

   @Column(name = "class", nullable = false)
   private VehicleClass vehicleClass;

   @Column(name = "model", nullable = false)
   private Model model;

}