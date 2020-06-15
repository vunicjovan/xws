package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {
    private Long id;
    private String name;
    private BrandDTO brand;

    public ModelDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.brand = new BrandDTO(model.getBrand());
    }

}
