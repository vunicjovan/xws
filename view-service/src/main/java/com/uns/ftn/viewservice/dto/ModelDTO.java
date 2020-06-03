package com.uns.ftn.viewservice.dto;

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
    private Boolean deleted;
    private BrandDTO brand;

}
