package com.uns.ftn.catalogservice.dto;

import com.uns.ftn.catalogservice.domain.Brand;
import com.uns.ftn.catalogservice.domain.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private Long id;
    private String name;
    private Set<Model> models = new HashSet<>();

    public BrandDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.models = brand.getModels();
    }
}
