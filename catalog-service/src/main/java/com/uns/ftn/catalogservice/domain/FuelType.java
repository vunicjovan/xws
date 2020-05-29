package com.uns.ftn.catalogservice.domain;

import lombok.*;

import javax.persistence.*;

/***********************************************************************
 * Module:  FuelType.java
 * Author:  Vunic
 * Purpose: Defines the Class FuelType
 ***********************************************************************/

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "fuelType")
@RequiredArgsConstructor
@NoArgsConstructor
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuelTypeId")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

}