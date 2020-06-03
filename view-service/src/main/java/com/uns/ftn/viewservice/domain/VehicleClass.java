package com.uns.ftn.viewservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vehicleClass")
public class VehicleClass {

    @Id
    @Column(name = "vehicleClassId")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

}
