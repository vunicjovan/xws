package com.uns.ftn.searchservice.domain;
/***********************************************************************
 * Module:  Advertisement.java
 * Author:  Vunic
 * Purpose: Defines the Class Advertisement
 ***********************************************************************/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Advertisement {

    @Id
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
    private Set<RentingInterval> rentingIntervals = new HashSet<>();

    @OneToOne
    private Vehicle vehicle;

    @Column(name = "ownerId")
    private Long ownerId;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photos = new HashSet<>();

}