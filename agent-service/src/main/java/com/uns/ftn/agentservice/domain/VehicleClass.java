package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  VehicleClass.java
 * Author:  Vunic
 * Purpose: Defines the Class VehicleClass
 ***********************************************************************/

import lombok.Data;

import java.util.*;

@Data
public class VehicleClass {

    private String name;
    private Set<Vehicle> vehicle;

}