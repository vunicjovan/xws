package com.uns.ftn.agent.dto;

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
    private Set<FuelTypeDTO> fuelTypes = new HashSet<>();
    private Set<GearboxTypeDTO> gearboxTypes = new HashSet<>();
    private Set<BrandDTO> brands = new HashSet<>();
    private Set<ModelDTO> models = new HashSet<>();
    private Set<VehicleClassDTO> vehicleClasses = new HashSet<>();
}
