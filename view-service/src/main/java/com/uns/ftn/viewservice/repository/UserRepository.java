package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long id);
}
