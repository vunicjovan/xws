package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Photo.java
 * Author:  Vunic
 * Purpose: Defines the Class Photo
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.util.*;

@Entity
@Data
public class Photo {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "path", nullable = false)
   private String path;

   @JsonIgnore
   @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
   private Advertisement advertisement;

}