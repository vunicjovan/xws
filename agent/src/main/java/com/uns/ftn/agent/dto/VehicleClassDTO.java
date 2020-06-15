package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.VehicleClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleClassDTO {

    private Long id;
    private String name;

    public VehicleClassDTO(VehicleClass vehicleClass) {
        this.id = vehicleClass.getId();
        this.name = vehicleClass.getName();
    }
}
