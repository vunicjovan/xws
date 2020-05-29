package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
