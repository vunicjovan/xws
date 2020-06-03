package com.uns.ftn.viewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleClassDTO {

    private Long id;
    private String name;
    private Boolean deleted;

}
