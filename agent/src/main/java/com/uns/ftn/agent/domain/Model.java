package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Model.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class Model
 * Created at MAY 2020
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Model {

   @Id
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @JsonIgnore
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Brand brand;

   @Column(name = "deleted")
   private Boolean deleted = false;
}