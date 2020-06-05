package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.RentingInterval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingIntervalRepository extends JpaRepository<RentingInterval, Long> {
}
