package com.uns.ftn.viewservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gearboxType")
public class GearboxType {

    @Id
    @Column(name = "gearboxTypeId")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @OneToMany(mappedBy = "gearboxType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles;

}
