package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Cart.java
 * Author:  Vunic
 * Purpose: Defines the Class Cart
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Cart {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private Set<Advertisement> advertisements;
   private SimpleUser simpleUser;

}