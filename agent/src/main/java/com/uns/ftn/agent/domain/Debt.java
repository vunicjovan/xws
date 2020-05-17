package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Debt.java
 * Author:  Vunic
 * Purpose: Defines the Class Debt
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Debt {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "value", nullable = false)
   private double value;
   
   private SimpleUser simpleUser;
   private Agent agent;

}