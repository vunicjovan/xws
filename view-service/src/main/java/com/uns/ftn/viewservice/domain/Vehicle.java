package com.uns.ftn.viewservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {

    @Id
    private Long id;

    @Column(name = "kilometersTraveled")
    private double kilometersTraveled;

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
