package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Permission.java
 * Author:  Vunic
 * Purpose: Defines the Class Permission
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Permission {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @ManyToMany(mappedBy = "permissions")
   private Set<Role> role;

}