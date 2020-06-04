package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long id);
}
