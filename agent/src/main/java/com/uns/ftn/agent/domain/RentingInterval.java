package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  RentingInterval.java
 * Author:  Vunic
 * Purpose: Defines the Class RentingInterval
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class RentingInterval {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "start", nullable = false)
   private Date startDate;

   @Column(name = "end", nullable = false)
   private Date endDate;
   
   private Advertisement advertisement;

}