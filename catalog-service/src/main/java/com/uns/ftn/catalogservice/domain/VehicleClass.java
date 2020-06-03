package com.uns.ftn.catalogservice.domain;

import lombok.Data;

import javax.persistence.*;

/***********************************************************************
 * Module:  VehicleClass.java
 * Author:  Vunic
 * Purpose: Defines the Class VehicleClass
 ***********************************************************************/

@Data
@Entity
@Table(name = "vehicleClass")
public class VehicleClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicleClassId")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

}