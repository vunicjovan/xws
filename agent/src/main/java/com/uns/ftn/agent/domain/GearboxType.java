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
@Data
public class GearboxType {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "gbtype_name", nullable = false)
   private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "gearbox_type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Vehicle> vehicles = new HashSet<Vehicle>();

}