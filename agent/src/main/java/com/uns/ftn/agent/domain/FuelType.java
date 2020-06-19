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
@Table(name = "fuelType")
@Data
public class FuelType {

   @Id
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "deleted")
   private Boolean deleted = false;
}