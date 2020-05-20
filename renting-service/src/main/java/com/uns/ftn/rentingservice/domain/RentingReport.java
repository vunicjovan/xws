package com.uns.ftn.rentingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentingReport {

   private Long id;
   private double kilometersTraveled;
   private String content;
   private Boolean limitBroken = false;
   
   private RentingRequest rentingRequest;

}