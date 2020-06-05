package com.uns.ftn.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int numberOfCancelation;
    private boolean blocked;
}
