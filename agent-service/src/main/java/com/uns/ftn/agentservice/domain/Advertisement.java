package com.uns.ftn.agentservice.domain;
/***********************************************************************
 * Module:  Advertisement.java
 * Author:  Vunic
 * Purpose: Defines the Class Advertisement
 ***********************************************************************/

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "kilometersPerDayLimit")
    private int kilometersPerDayLimit = -1;

    @Column(name = "collisionDamageWaiver")
    private Boolean collisionDamageWaiver = false;

    @Column(name = "rating")
    private double rating = 0;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RentingInterval> rentingIntervals;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @Column(name = "ownerId")
    private Long ownerId;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photos;

}