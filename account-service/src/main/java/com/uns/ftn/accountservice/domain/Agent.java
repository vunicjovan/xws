package com.uns.ftn.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Company company;

    public Agent(User user, Company company) {
        this.user = user;
        this.company = company;
    }

}