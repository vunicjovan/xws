package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  FuelType.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class FuelType
 * Created at MAY 2020
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class FuelType {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "ftype_name", nullable = false)
   private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "fuel_type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Vehicle> vehicles = new HashSet<Vehicle>();
}