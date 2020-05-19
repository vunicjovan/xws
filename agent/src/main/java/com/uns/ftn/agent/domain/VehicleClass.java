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
@Data
public class VehicleClass {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "class_name", nullable = false)
   private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "vehicle_class", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Vehicle> vehicles = new HashSet<Vehicle>();
}