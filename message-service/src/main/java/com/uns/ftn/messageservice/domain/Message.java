package com.uns.ftn.messageservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "timestamp", nullable = false)
   private Date timestamp;

   @Column(name = "senderId", nullable = false)
   private Long senderId;

   @Column(name = "receiverId", nullable = false)
   private Long receiverId;

   @Column(name = "senderUsername", nullable = false)
   private String senderUsername;

}