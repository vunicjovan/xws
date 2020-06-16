package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String mail);

    User findByIdAndDeleted(Long id, Boolean deleted);
}
