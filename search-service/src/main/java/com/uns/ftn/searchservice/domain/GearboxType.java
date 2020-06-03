package com.uns.ftn.searchservice.domain;
/***********************************************************************
 * Module:  GearboxType.java
 * Author:  Vunic
 * Purpose: Defines the Class GearboxType
 ***********************************************************************/

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
@Table(name = "gearboxType")
public class GearboxType {

    @Id
    @Column(name = "gearboxTypeId")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

}