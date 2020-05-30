package com.uns.ftn.catalogservice.dto;

import com.uns.ftn.catalogservice.domain.GearboxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearboxTypeDTO {

    private String name;
    private Boolean deleted = false;

    public GearboxTypeDTO(GearboxType gbt) {
        this.name = gbt.getName();
        this.deleted = gbt.getDeleted();
    }

}
