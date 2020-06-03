package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
