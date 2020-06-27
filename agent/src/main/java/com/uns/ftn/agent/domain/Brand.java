package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Brand.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class Brand
 * Created at MAY 2020
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

   @Id
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "deleted")
   private Boolean deleted = false;
}