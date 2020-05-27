package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
