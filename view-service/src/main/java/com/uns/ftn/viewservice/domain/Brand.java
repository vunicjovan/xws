package com.uns.ftn.viewservice.domain;

import lombok.*;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Brand {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Model> models;

}
