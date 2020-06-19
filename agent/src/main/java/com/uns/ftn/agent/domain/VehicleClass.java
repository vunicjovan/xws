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
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "deleted")
   private Boolean deleted = false;
}