package com.uns.ftn.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {

    private Long id;
    private String name;

    private java.util.Set<Permission> permission;
    private java.util.Set<User> user;

}