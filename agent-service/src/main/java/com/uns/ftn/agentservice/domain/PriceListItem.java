package com.uns.ftn.agentservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/***********************************************************************
 * Module:  PriceListItem.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceListItem
 ***********************************************************************/

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "priceListItem")
public class PriceListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dailyPrice")
    private double dailyPrice;

    @Column(name = "cdwPrice")
    private double cdwPrice;

    @Column(name = "debtPrice")
    private double debtPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PriceList priceList;

    @JsonIgnore
    @OneToMany(mappedBy = "priceListItem", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements;

}