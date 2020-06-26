package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Cart;
import com.uns.ftn.rentingservice.domain.CartItem;
import com.uns.ftn.rentingservice.dto.CartDTO;
import com.uns.ftn.rentingservice.dto.CartItemDTO;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.repository.CartItemRepository;
import com.uns.ftn.rentingservice.repository.CartRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

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

        if(cartItemsDtos.size() == 0) {
            LOGGER.warn("Cart item list size 0 for userId={}", userId);
        }

        return new ResponseEntity<>(new CartDTO(cartItemsDtos), HttpStatus.OK);
    }

    public ResponseEntity<?> addCartItem(Long userID, Long advertisementId) {
        LOGGER.debug("Adding new cartItem[userId={}, advertisementId={}]", userID, advertisementId);
        Cart cart = findOneCart(userID);

        if (cartItemService.findCartItem(cart.getId(), advertisementId) != null) {
            LOGGER.warn("Skipping new cartItem[userId={}, advertisementId={}] because it is already in cart",
                    userID, advertisementId);
            throw new BadRequestException("Chosen advertisement is already added to cart.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setAdvertismentId(advertisementId);

        cartItem = cartItemService.save(cartItem);
        LOGGER.info("Database entry: created new cartItem[id={}, cartId{}]", cartItem.getId(),
                cartItem.getCart().getId());

        LOGGER.debug("Finished adding new cartItem[userId={}, advertisementId={}]", userID, advertisementId);
        return new ResponseEntity<>(new CartItemDTO(cartItem), HttpStatus.CREATED);
    }

    public ResponseEntity<?> removeCartItem(Long userId, Long advertisementId) {
        LOGGER.debug("Removing cartItem[userId={}, advertisementId={}]", userId, advertisementId);
        Cart cart = findOneCart(userId);
        CartItem cartItem = cartItemService.findCartItem(cart.getId(), advertisementId);

        if (cartItem == null) {
            LOGGER.warn("Skipping remove cartItem[userId={}, advertisementId={}] because it doesn't exist in cart",
                    userId, advertisementId);
            throw new BadRequestException("Chosen advertisement does not exist in cart.");
        }

        cartItemService.deleteCartItem(cartItem.getId());
        LOGGER.info("Database entry: deleted cartItem=[id={}, cartId={}]", cartItem.getId(), cart.getId());

        LOGGER.debug("Finished removing cartItem[userId={}, advertisementId={}]", userId, advertisementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void createCart(Long userId) {
        LOGGER.debug("Creating new cart[userId={}]", userId);
        if (cartRepository.findByUserId(userId) != null) {
            LOGGER.warn("User with userId={} already has a cart", userId);
            throw new BadRequestException("User already has a cart!");
        }

        Cart cart = new Cart(userId);
        cartRepository.save(cart);
        LOGGER.info("Database entry: created new cart[id={}, userId={}]", cart.getId(), userId);
        LOGGER.debug("Finished creating cart[userId={}]", userId);

    }

    public Cart findOneCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }


}
