package com.uns.ftn.catalogservice.domain;

import lombok.Data;

import javax.persistence.*;

/***********************************************************************
 * Module:  FuelType.java
 * Author:  Vunic
 * Purpose: Defines the Class FuelType
 ***********************************************************************/

@Data
@Entity
@Table(name = "fuelType")
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuelTypeId")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}