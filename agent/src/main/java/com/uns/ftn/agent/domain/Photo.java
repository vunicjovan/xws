package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Photo.java
 * Author:  Vunic
 * Purpose: Defines the Class Photo
 ***********************************************************************/

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

   @Column(name = "image", nullable = false)
   private Image image;
   private Advertisement advertisement;

}