package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.RentingInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentingIntervalRepository extends JpaRepository<RentingInterval, Long> {
}
