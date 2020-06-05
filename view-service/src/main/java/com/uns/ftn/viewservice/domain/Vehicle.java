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
    private int kilometersTraveled;

    @Column(name = "childSeatNumber")
    private int childSeatNumber;

    @Column(name = "hasAndroid")
    private Boolean hasAndroid = false;

    @JsonIgnore
    @OneToOne
    private Advertisement advertisement;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fuelTypeId")
    private FuelType fuelType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "gearboxTypeId")
    private GearboxType gearboxType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicleClassId")
    private VehicleClass vehicleClass;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "modelId")
    private Model model;
}
