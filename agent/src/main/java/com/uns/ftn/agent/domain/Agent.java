package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Agent.java
 * Author:  Vunic
 * Purpose: Defines the Class Agent
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Agent {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private Set<Advertisement> advertisements;
   private User user;
   private Set<Debt> debts;
   private Company company;

}