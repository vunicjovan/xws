package com.uns.ftn.rentingservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "rentingReport")
public class RentingReport {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "kilometersTraveled", nullable = false)
   private double kilometersTraveled;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "limitBroken", nullable = false)
   private Boolean limitBroken = false;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JsonIgnore
   private RentingRequest rentingRequest;

}