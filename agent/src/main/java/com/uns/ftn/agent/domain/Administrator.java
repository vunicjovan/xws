package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Administrator.java
 * Author:  Jovan
 * Purpose: Defines the Class Administrator
 ***********************************************************************/

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Administrator {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private User user;

}