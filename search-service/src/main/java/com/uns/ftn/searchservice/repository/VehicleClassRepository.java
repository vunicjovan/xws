package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleClassRepository extends JpaRepository<VehicleClass, Long> {
}
