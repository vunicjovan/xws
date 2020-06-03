package com.uns.ftn.viewservice.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private Long id;
    private String name;
    private Boolean deleted;
    private Set<ModelDTO> models;

}
