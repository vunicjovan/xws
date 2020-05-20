package com.uns.ftn.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permission {

    private Long id;
    private String name;

    private java.util.Set<Role> role;

}