package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  GearboxType.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class GearboxType
 * Create at MAY 2020
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "gearboxType")
@Data
public class GearboxType {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "deleted")
   private Boolean deleted = false;

   @JsonIgnore
   @OneToMany(mappedBy = "gearboxType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Vehicle> vehicles = new HashSet<Vehicle>();

}