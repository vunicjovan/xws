package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.ResetToken;
import com.uns.ftn.accountservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

    ResetToken findByToken(String token);

    ResetToken findByUser(User user);

}
