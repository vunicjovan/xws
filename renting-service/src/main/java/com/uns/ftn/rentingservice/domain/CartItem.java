package com.uns.ftn.rentingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {

    private Long id;
    private Long advertismentId;
}
