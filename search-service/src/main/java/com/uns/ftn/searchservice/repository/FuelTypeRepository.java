package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
}
