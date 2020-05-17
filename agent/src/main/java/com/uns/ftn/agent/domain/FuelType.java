package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  FuelType.java
 * Author:  Vunic
 * Purpose: Defines the Class FuelType
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class FuelType {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;
   private Set<Vehicle> vehicle;

}