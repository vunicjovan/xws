package com.uns.ftn.rentingservice.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Debt {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "simpleUserId", nullable = false)
   private Long simpleUserId;

   @Column(name = "agentId", nullable = false)
   private Long agentId;

   @Column(name = "value", nullable = false)
   private double value;

}