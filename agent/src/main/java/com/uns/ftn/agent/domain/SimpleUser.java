package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  SimpleUser.java
 * Author:  Vunic
 * Purpose: Defines the Class SimpleUser
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class SimpleUser {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "ad_number", nullable = false)
   private int numberOfAds = 0;

   @Column(name = "blocked", nullable = false)
   private Boolean blocked = False;

   @Column(name = "cancelation_number", nullable = false)
   private int numberOfCancelations = 0;
   
   private User user;
   private Cart cart;
   private Set<RentingRequest> rentingRequests;
   private Set<Comment> comments;
   private Set<Debt> debts;

}