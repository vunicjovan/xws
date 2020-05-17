package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  GearboxType.java
 * Author:  Vunic
 * Purpose: Defines the Class GearboxType
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class GearboxType {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;
   private Set<Vehicle> vehicle;

}