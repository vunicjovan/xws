package com.uns.ftn.catalogservice.dto;

import com.uns.ftn.catalogservice.domain.GearboxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearboxTypeDTO {

    private Long id;
    private String name;
    private Boolean deleted = false;

    public GearboxTypeDTO(GearboxType gbt) {
        this.id = gbt.getId();
        this.name = gbt.getName();
        this.deleted = gbt.getDeleted();
    }

    public GearboxTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
