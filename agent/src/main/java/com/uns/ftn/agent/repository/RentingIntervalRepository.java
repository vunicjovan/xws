package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.RentingInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentingIntervalRepository extends JpaRepository<RentingInterval, Long> {
}
