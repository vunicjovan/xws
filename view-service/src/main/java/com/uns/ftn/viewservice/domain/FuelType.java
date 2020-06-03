package com.uns.ftn.viewservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "fuelType")
@RequiredArgsConstructor
@NoArgsConstructor
public class FuelType {

    @Id
    @Column(name = "fuelTypeId")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "fuelType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles;

}
