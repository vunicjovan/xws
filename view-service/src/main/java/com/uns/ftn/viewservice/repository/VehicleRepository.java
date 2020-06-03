package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
