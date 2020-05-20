package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  Comment.java
 * Author:  Vunic
 * Purpose: Defines the Class Comment
 ***********************************************************************/

import lombok.Data;

@Data
public class Comment {
   private String title;
   private String content;
   private String author;
   private Boolean allowed = false;

   private Long simpleUserId;
   private Advertisement advertisement;
}