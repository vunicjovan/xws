package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.GearboxType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GearboxTypeDTO {
    private Long id;
    private String name;

    public GearboxTypeDTO(GearboxType gearboxType) {
        this.id = gearboxType.getId();
        this.name = gearboxType.getName();
    }

    public GearboxTypeDTO(rs.ac.uns.ftn.catalog.GearboxType gearboxType) {
        this.id = gearboxType.getId();
        this.name = gearboxType.getName();
    }
}
