package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
