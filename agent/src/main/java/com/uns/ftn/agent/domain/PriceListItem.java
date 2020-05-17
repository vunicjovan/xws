package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  PriceListItem.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceListItem
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class PriceListItem {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "daily_price", nullable = false)
   private double dailyPrice;

   @Column(name = "cdw_price", nullable = false)
   private double cdwPrice;

   @Column(name = "debt_price", nullable = false)
   private double debtPrice;
   
   private PriceList priceList;

}