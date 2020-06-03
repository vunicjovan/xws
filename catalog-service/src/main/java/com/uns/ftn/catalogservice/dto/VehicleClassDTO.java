package com.uns.ftn.catalogservice.dto;

import com.uns.ftn.catalogservice.domain.VehicleClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleClassDTO {

    private Long id;
    private String name;
    private Boolean deleted = false;

    public VehicleClassDTO(VehicleClass vehicleClass) {
        this.id = vehicleClass.getId();
        this.name = vehicleClass.getName();
        this.deleted = vehicleClass.getDeleted();
    }

    public VehicleClassDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
