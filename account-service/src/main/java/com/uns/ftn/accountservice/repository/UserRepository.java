package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.Role;
import com.uns.ftn.accountservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String mail);

    User findByIdAndDeleted(Long id, Boolean deleted);
}
