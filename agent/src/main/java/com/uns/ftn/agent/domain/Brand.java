package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Brand.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class Brand
 * Created at MAY 2020
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Brand {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Model> models = new HashSet<Model>();
}