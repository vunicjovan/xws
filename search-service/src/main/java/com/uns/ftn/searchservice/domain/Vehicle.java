package com.uns.ftn.searchservice.domain;
/***********************************************************************
 * Module:  Vehicle.java
 * Author:  Vunic
 * Purpose: Defines the Class Vehicle
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Vehicle {

    @Id
    private Long id;

    @Column(name = "kilometersTraveled")
    private int kilometersTraveled;

    @Column(name = "childSeatNumber")
    private int childSeatNumber;

    @Column(name = "hasAndroid")
    private Boolean hasAndroid = false;

    @OneToOne()
    @JoinColumn(name = "fuelTypeId")
    private FuelType fuelType;

    @OneToOne
    @JoinColumn(name = "gearboxTypeId")
    private GearboxType gearboxType;

    @OneToOne
    @JoinColumn(name = "vehicleClassId")
    private VehicleClass vehicleClass;

    @OneToOne
    @JoinColumn(name = "modelId")
    private Model model;

}