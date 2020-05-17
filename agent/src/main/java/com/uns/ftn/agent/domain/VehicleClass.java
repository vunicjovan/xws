package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  VehicleClass.java
 * Author:  Vunic
 * Purpose: Defines the Class VehicleClass
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class VehicleClass {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;
   
   private Set<Vehicle> vehicle;

}