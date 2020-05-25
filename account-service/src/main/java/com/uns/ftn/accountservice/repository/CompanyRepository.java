package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByBusinessNumber(String businessNumber);
}
