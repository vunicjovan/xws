package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  RentingInterval.java
 * Author:  Vunic
 * Purpose: Defines the Class RentingInterval
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "rentingInterval")
@Data
public class RentingInterval {

   @Id
   private Long id;

   @Column(name = "startDate", nullable = false)
   private Date startDate;

   @Column(name = "endDate", nullable = false)
   private Date endDate;

   @JsonIgnore
   @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
   private Advertisement advertisement;

}