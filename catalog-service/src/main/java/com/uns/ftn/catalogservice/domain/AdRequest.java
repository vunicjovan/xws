package com.uns.ftn.catalogservice.domain; /***********************************************************************
 * Module:  AdRequest.java
 * Author:  Jovan
 * Purpose: Defines the Class AdRequest
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "adRequest")
public class AdRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fuelTypeId")
    private FuelType fuelType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gearboxTypeId")
    private GearboxType gearboxType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicleClassId")
    private VehicleClass vehicleClass;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modelId")
    private Model model;

}