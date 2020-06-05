package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCart_IdAndAdvertismentId(Long cartId, Long advertisementId);
}
