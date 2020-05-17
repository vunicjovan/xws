package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  RentingRequest.java
 * Author:  Vunic
 * Purpose: Defines the Class RentingRequest
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class RentingRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "start", nullable = false)
   private Date startDate;

   @Column(name = "end", nullable = false)
   private Date endDate;
   
   private SimpleUser simpleUser;
   private Set<RentingReport> rentingReports;
   private Set<Advertisement> advertisements;

}