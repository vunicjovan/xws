package com.uns.ftn.agentservice.domain;
/***********************************************************************
 * Module:  Vehicle.java
 * Author:  Vunic
 * Purpose: Defines the Class Vehicle
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kilometersTraveled")
    private int kilometersTraveled;

    @Column(name = "childSeatNumber")
    private int childSeatNumber;

    @Column(name = "hasAndroid")
    private Boolean hasAndroid = false;

    @JsonIgnore
    @OneToOne
    private Advertisement advertisement;

    @Column(name = "fuelTypeId", nullable = false)
    private Long fuelTypeId;

    @Column(name = "gearboxTypeId", nullable = false)
    private Long gearboxTypeId;

    @Column(name = "vehicleClassId", nullable = false)
    private Long vehicleClassId;

    @Column(name = "modelId", nullable = false)
    private Long modelId;

}