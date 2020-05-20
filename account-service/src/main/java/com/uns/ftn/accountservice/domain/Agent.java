package com.uns.ftn.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Agent {

    private Long id;
    private User user;
    private Company company;

}