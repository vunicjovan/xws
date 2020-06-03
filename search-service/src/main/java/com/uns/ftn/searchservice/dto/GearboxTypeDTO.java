package com.uns.ftn.searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GearboxTypeDTO {
    private Long id;
    private String name;
    private Boolean deleted = false;
}
