package com.uns.ftn.catalogservice.domain; /***********************************************************************
 * Module:  Brand.java
 * Author:  Vunic
 * Purpose: Defines the Class Brand
 ***********************************************************************/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Model> models;

    @Column(nullable = false)
    private Boolean deleted = false;

}