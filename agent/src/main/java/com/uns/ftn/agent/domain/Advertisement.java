package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Advertisement.java
 * Author:  Jovan
 * Purpose: Defines the Class Advertisement
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "kilometersPerDayLimit", nullable = false)
    private int kilometersPerDayLimit = -1;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "collisionDamageWaiver", nullable = false)
    private Boolean collisionDamageWaiver = false;

   @Column(name = "rating", nullable = false)
   private double rating = 0;

   @JsonIgnore
   @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<RentingInterval> rentingIntervals;

   @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Vehicle vehicle;

   @Column(name = "ownerId")
   private Long ownerId;

   @JsonIgnore
   @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Photo> photos;

   @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Comment> comments;

   @ManyToOne(fetch = FetchType.EAGER)
   private PriceListItem priceListItem;

}