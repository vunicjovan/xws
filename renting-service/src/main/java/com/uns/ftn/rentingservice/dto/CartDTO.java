package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Set<CartItemDTO> cartItemDTOS = new HashSet<>();
}
