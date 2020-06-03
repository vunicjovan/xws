package com.uns.ftn.viewservice.domain;

import lombok.*;

import javax.persistence.*;

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

}
