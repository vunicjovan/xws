package com.uns.ftn.catalogservice.domain; /***********************************************************************
 * Module:  GearboxType.java
 * Author:  Vunic
 * Purpose: Defines the Class GearboxType
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "gearboxType")
public class GearboxType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gearboxTypeId")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}