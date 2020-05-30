package com.uns.ftn.agentservice.domain;
/***********************************************************************
 * Module:  PriceList.java
 * Author:  Vunic
 * Purpose: Defines the Class PriceList
 ***********************************************************************/

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "priceList")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "validFrom")
    private Date validFrom;

    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PriceListItem> priceListItem;

}