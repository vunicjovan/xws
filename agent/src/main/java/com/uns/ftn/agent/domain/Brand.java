package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Brand.java
 * Author:  Vunic
 * Purpose: Defines the Class Brand
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Brand {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;
   
   private Set<Model> models;

}