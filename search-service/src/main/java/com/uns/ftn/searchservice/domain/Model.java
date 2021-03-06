package com.uns.ftn.searchservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/***********************************************************************
 * Module:  Model.java
 * Author:  Vunic
 * Purpose: Defines the Class Model
 ***********************************************************************/

@Data
@Entity
public class Model {

    @Id
    @Column(name = "modelId")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Brand brand;

    @Column(name = "deleted")
    private Boolean deleted = false;

}