package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long advertisementId;
    private Long cartId;

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.advertisementId = cartItem.getAdvertismentId();
        this.cartId = cartItem.getCart().getId();
    }
}
