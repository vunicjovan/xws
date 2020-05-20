package com.uns.ftn.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleUser {

    private Long id;
    private int numberOfAds = 0;
    private Boolean blocked = false;
    private int numberOfCancelations = 0;

    private User user;

}