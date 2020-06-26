package com.uns.ftn.rentingservice.controller;

import com.uns.ftn.rentingservice.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasAuthority('GET_CART_ITEMS')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return cartService.getCartItems(id);
    }

    @PreAuthorize("hasAuthority('ADD_CART_ITEM')")
    @PostMapping("/{userId}/item/{id}")
    public ResponseEntity<?> addItem(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
        return cartService.addCartItem(userId, id);
    }

    @PreAuthorize("hasAuthority('DELETE_CART_ITEM')")
    @DeleteMapping("/{userId}/item/{id}")
    public ResponseEntity<?> delete(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
        return cartService.removeCartItem(userId, id);
    }

}
