package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.RentingInterval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RentingIntervalRepository extends JpaRepository<RentingInterval, Long> {
    Set<RentingInterval> findAllByAdvertisementId(Long id);
}
