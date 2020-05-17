package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Model.java
 * Author:  Vunic
 * Purpose: Defines the Class Model
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Model {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;
   private Set<Vehicle> vehicle;
   private Brand brand;

}