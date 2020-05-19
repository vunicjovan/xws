package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Photo.java
 * Author:  Dusan Petkovic
 * Purpose: Defines the Class Photo
 * Created at MAY 2020
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

   @Column(name = "image_path", nullable = false)
   private String path;

   @ManyToOne
   @JoinColumn(name = "advertisment_id", nullable = false)
   private Advertisement advertisement;

}