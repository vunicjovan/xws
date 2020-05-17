package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Role.java
 * Author:  Vunic
 * Purpose: Defines the Class Role
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @ManyToMany(mappedBy = "roles")
   private Set<User> users;

   @ManyToMany
   @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
   private Set<Permission> permissions;

}