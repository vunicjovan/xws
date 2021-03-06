package com.uns.ftn.rentingservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "rentingReport")
public class RentingReport {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @Column(name = "kilometersTraveled", nullable = false)
    private int kilometersTraveled;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "limitBroken", nullable = false)
   private Boolean limitBroken = false;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   @JsonIgnore
   private RentingRequest rentingRequest;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   @JsonIgnore
   private Advertisement advertisement;

}