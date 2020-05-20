package com.uns.ftn.agentservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

/***********************************************************************
 * Module:  PriceListItem.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceListItem
 ***********************************************************************/

@Data
@AllArgsConstructor
@Entity
@Table(name = "priceListItem")
public class PriceListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dailyPrice")
    private double dailyPrice;

    @Column(name = "cdwPrice")
    private double cdwPrice;

    @Column(name = "debtPrice")
    private double debtPrice;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PriceList priceList;

}