package com.uns.ftn.accountservice.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
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