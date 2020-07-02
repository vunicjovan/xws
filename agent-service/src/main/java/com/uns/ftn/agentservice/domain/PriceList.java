package com.uns.ftn.agentservice.domain;
/***********************************************************************
 * Module:  PriceList.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceList
 ***********************************************************************/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "priceList")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ownerId")
    private Long ownerId;

    @Column(name = "discount")
    private double discount = 0;

    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PriceListItem> priceListItem;

}