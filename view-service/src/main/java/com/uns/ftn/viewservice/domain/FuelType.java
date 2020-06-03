package com.uns.ftn.viewservice.domain;

import lombok.*;

import javax.persistence.*;

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

}
