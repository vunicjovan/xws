package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.CartItem;
import com.uns.ftn.rentingservice.dto.CartItemDTO;
import com.uns.ftn.rentingservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }


    public CartItem findCartItem(Long cartId, Long advertisementId) {
        return cartItemRepository.findByCart_IdAndAdvertismentId(cartId, advertisementId);
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

}
