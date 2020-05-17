package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  User.java
 * Author:  Vunic
 * Purpose: Defines the Class User
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table("Users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = False;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    private Set<Message> sentMessages;

    private Set<Message> receivedMessages;

    private Administrator administrator;

    private SimpleUser simpleUser;

    private Agent agent;

}