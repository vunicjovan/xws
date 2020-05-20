package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  Comment.java
 * Author:  Vunic
 * Purpose: Defines the Class Comment
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "title", nullable = false)
   private String title;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "allowed")
   private Boolean allowed = false;

   @Column(name = "userId")
   private Long userId;

   @JsonIgnore
   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Advertisement advertisement;
}