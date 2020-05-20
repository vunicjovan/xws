package com.uns.ftn.rentingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Cart {

    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;

}
