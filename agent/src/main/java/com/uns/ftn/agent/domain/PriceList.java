package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  PriceList.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceList
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class PriceList {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "valid_from", nullable = false)
   private Date validFrom;
   
   private Set<PriceListItem> priceListItems;
   private Company company;

}