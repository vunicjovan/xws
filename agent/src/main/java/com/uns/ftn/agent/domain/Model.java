package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Model.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class Model
 * Created at MAY 2020
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Model {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @ManyToOne
   @JoinColumn(name = "brand_id", nullable = false)
   private Brand brand;

}