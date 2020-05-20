package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  Vehicle.java
 * Author:  Vunic
 * Purpose: Defines the Class Vehicle
 ***********************************************************************/

import lombok.Data;

@Data
public class Vehicle {

    private double kilometersTraveled;
    private int childSeatNumber;
    private Boolean hasAndroid = false;

    private Advertisement advertisement;
    private Long fuelTypeId;
    private Long gearboxTypeId;
    private VehicleClass vehicleClass;
    private Long modelId;

}