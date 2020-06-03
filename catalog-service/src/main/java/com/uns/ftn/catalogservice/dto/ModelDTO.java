package com.uns.ftn.catalogservice.dto;

import com.uns.ftn.catalogservice.domain.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModelDTO {
    private Long id;
    private String name;
    private BrandDTO brand;
    private Boolean deleted = false;

    public ModelDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.brand = new BrandDTO(model.getBrand());
        this.deleted = model.getDeleted();
    }
}
