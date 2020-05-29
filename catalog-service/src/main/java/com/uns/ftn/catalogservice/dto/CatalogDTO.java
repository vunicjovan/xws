package com.uns.ftn.catalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDTO {
    private Set<FuelTypeDTO> fuelTypeDTOSet = new HashSet<>();
}
