package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    List<Debt> findAllBySimpleUserIdAndDeleted(Long id, boolean deleted);
    List<Debt> findAllByAgentIdAndDeleted(Long id, boolean deleted);
}
