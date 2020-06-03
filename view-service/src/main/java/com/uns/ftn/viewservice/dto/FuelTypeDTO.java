package com.uns.ftn.viewservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FuelTypeDTO {

    private Long id;
    private String name;
    private Boolean deleted;

}
