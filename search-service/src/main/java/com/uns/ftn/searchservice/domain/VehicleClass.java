package com.uns.ftn.searchservice.domain;

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
    @Column(name = "vehicleClassId")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

}