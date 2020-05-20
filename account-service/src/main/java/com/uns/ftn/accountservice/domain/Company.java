package com.uns.ftn.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Company {

    private Long id;
    private String state;
    private String city;
    private String street;
    private String businessNumber;

    private java.util.Set<Agent> agents;

}