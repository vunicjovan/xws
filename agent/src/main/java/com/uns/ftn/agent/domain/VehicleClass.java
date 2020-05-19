package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  VehicleClass.java
 * Author:  Vunic
 * Purpose: Defines the Class VehicleClass
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "vehicleClass")
@Data
public class VehicleClass {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "vehicleClass", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Vehicle> vehicles = new HashSet<Vehicle>();
}