package com.uns.ftn.catalogservice.domain;
/***********************************************************************
 * Module:  GearboxType.java
 * Author:  Vunic
 * Purpose: Defines the Class GearboxType
 ***********************************************************************/

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "gearboxType")
public class GearboxType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gearboxTypeId")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

}