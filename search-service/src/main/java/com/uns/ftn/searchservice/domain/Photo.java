package com.uns.ftn.searchservice.domain;
/***********************************************************************
 * Module:  Photo.java
 * Author:  Vunic
 * Purpose: Defines the Class Photo
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Photo {

    @Id
    private Long id;

    @Column(name = "path")
    private String path;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Advertisement advertisement;

}