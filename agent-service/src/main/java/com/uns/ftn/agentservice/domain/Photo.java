package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  Photo.java
 * Author:  Vunic
 * Purpose: Defines the Class Photo
 ***********************************************************************/

import lombok.Data;

@Data
public class Photo {

    private String path;
    private Advertisement advertisement;

}