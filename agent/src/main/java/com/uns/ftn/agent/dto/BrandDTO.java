package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private Long id;
    private String name;
    private Boolean deleted;

    public BrandDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }

    public BrandDTO(rs.ac.uns.ftn.catalog.Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.deleted = brand.isDeleted();
    }
}
