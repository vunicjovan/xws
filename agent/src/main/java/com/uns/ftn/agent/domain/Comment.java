package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Comment.java
 * Author:  Vunic
 * Purpose: Defines the Class Comment
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Comment")
@Data
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "title", nullable = false)
   private String title;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "author", nullable = false)
   private String author;

   @Column(name = "allowed", nullable = false)
   private Boolean allowed = false;
   
   private SimpleUser simpleUser;
   private Advertisement advertisement;

}