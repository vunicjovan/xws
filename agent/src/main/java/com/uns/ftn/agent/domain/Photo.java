package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Photo.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class Photo
 * Created at MAY 2020
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
   @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
   private Advertisement advertisement;

}