package com.uns.ftn.agentservice.domain; /***********************************************************************
 * Module:  RentingInterval.java
 * Author:  Vunic
 * Purpose: Defines the Class RentingInterval
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "rentingInterval")
public class RentingInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Advertisement advertisement;

}