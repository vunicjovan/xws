package com.uns.ftn.messageservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Message {

   private Long id;
   private String content;
   private Date timestamp;
   private Long senderId;
   private Long receiverId;

}