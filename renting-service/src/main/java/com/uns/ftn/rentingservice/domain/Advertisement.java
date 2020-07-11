package com.uns.ftn.rentingservice.domain;
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

    @Column(name = "ownerId")
    private Long ownerId;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<RentingInterval> rentingIntervals = new HashSet<>();

    @ManyToMany(mappedBy = "advertisements", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<RentingRequest> rentingRequests;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private  Set<Comment> comments;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private  Set<RentingReport> rentingReports;

}