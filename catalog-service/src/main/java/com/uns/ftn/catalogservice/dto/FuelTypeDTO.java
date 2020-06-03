package com.uns.ftn.catalogservice.dto;

import com.uns.ftn.catalogservice.domain.FuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeDTO {
    private Long id;
    private String name;
    private Boolean deleted = false;

    public FuelTypeDTO(FuelType fuelType) {
        this.id = fuelType.getId();
        this.name = fuelType.getName();
        this.deleted = fuelType.getDeleted();
    }

    public FuelTypeDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
