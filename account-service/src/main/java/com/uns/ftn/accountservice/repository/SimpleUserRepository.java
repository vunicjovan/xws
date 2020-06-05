package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.SimpleUser;
import com.uns.ftn.accountservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {

    SimpleUser findByUser(User user);

}
