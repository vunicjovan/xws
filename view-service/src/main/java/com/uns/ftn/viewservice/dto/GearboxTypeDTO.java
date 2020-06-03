package com.uns.ftn.viewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearboxTypeDTO {

    private Long id;
    private String name;
    private Boolean deleted;

}
