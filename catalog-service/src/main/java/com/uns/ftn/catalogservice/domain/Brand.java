package com.uns.ftn.catalogservice.domain; /***********************************************************************
 * Module:  Brand.java
 * Author:  Vunic
 * Purpose: Defines the Class Brand
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Model> models;

}