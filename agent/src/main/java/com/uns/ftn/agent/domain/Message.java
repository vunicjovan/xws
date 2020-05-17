package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Message.java
 * Author:  Vunic
 * Purpose: Defines the Class Message
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Message {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "timestamp", nullable = false)
   private Date timestamp;
   
   private User sender;
   private User receiver;

}