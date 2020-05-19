package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  GearboxType.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class GearboxType
 * Create at MAY 2020
 ***********************************************************************/

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class GearboxType {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

}