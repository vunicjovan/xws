package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
