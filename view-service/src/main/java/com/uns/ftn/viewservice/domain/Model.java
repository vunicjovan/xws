package com.uns.ftn.viewservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Model {

    @Id
    @Column(name = "modelId")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Brand brand;

}
