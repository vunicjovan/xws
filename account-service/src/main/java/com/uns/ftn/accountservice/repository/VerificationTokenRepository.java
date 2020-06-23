package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}
