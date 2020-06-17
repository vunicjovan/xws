package com.uns.ftn.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "simpleUser")
public class SimpleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numberOfAds")
    private int numberOfAds = 0;

    @Column(name = "blocked")
    private Boolean blocked = false;

    @Column(name = "numberOfCancelations")
    private int numberOfCancelations = 0;

    @OneToOne
    private User user;

    public SimpleUser(User user) {
        this.user = user;
    }

}