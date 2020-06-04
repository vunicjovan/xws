package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Cart;
import com.uns.ftn.rentingservice.domain.CartItem;
import com.uns.ftn.rentingservice.dto.CartDTO;
import com.uns.ftn.rentingservice.dto.CartItemDTO;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.repository.CartItemRepository;
import com.uns.ftn.rentingservice.repository.CartRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemService cartItemService;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    public ResponseEntity<?> getCartItems(Long userId) {
        Cart cart = findOneCart(userId);
        Set<CartItemDTO> cartItemsDtos = cart.getCartItems().stream().map(cartItem -> new CartItemDTO(cartItem))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(new CartDTO(cartItemsDtos), HttpStatus.OK);
    }

    public ResponseEntity<?> addCartItem(Long userID, Long advertisementId) {
        Cart cart = findOneCart(userID);

        if (cartItemService.findCartItem(cart.getId(), advertisementId) != null) {
            throw new BadRequestException("Chosen advertisement is already added to cart.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setAdvertismentId(advertisementId);

        cartItem = cartItemService.save(cartItem);

        return new ResponseEntity<>(new CartItemDTO(cartItem), HttpStatus.CREATED);
    }

    public ResponseEntity<?> removeCartItem(Long userId, Long advertisementId) {
        Cart cart = findOneCart(userId);
        CartItem cartItem = cartItemService.findCartItem(cart.getId(), advertisementId);

        if (cartItem == null) {
            throw new BadRequestException("Chosen advertisement does not exist in cart.");
        }

        cartItemService.deleteCartItem(cartItem.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findOneCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }


}
