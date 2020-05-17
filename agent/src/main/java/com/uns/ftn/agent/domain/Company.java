package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Company.java
 * Author:  Vunic
 * Purpose: Defines the Class Company
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Company {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "state", nullable = false)
   private String state;

   @Column(name = "city", nullable = false)
   private String city;

   @Column(name = "street", nullable = false)
   private String street;

   @Column(name = "business_number", nullable = false)
   private String businessNumber;
   
   private Set<Agent> agents;
   private Set<PriceList> priceList;

}