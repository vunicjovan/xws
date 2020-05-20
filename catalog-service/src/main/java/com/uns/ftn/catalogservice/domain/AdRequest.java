package com.uns.ftn.catalogservice.domain; /***********************************************************************
 * Module:  AdRequest.java
 * Author:  Jovan
 * Purpose: Defines the Class AdRequest
 ***********************************************************************/

import lombok.Data;

import java.util.*;

@Data
public class AdRequest {

    private FuelType fuelType;
    private GearboxType gearboxType;
    private VehicleClass vehicleClass;
    private Model model;

}