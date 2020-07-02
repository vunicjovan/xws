package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Long> {
}
