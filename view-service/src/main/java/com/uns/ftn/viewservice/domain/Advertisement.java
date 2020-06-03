package com.uns.ftn.viewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertisement {

    @Id
    private Long id;

    @Column(name = "place")
    private String place;

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

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RentingInterval> rentingIntervals;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @Column(name = "ownerId")
    private Long ownerId;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photos;
}
