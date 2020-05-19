package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Agent.java
 * Author:  Vunic
 * Purpose: Defines the Class Agent
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Agent {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @JsonIgnore
   @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Advertisement> advertisements;

}