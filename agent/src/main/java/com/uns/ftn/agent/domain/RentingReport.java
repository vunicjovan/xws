package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  RentingReport.java
 * Author:  Vunic
 * Purpose: Defines the Class RentingReport
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class RentingReport {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "km_traveled", nullable = false)
   private double kilometersTraveled;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "limit_broken", nullable = false)
   private Boolean limitBroken = false;
   
   private RentingRequest rentingRequest;

}