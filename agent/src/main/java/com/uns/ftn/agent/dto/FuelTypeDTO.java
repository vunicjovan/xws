package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.FuelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeDTO {

    private Long id;
    private String name;

    public FuelTypeDTO(FuelType fuelType) {
        this.id = fuelType.getId();
        this.name = fuelType.getName();
    }

    public FuelTypeDTO(rs.ac.uns.ftn.catalog.FuelType fuelType)  {
        this.id = fuelType.getId();
        this.name = fuelType.getName();
    }
}
