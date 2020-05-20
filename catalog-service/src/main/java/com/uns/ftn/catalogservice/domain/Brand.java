package com.uns.ftn.catalogservice.domain; /***********************************************************************
 * Module:  Brand.java
 * Author:  Vunic
 * Purpose: Defines the Class Brand
 ***********************************************************************/

import lombok.Data;

import java.util.*;

@Data
public class Brand {

    private String name;
    private Set<Model> models;

}