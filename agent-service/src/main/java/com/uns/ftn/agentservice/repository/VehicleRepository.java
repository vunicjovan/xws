package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Set<Vehicle> findAllByModelId(Long modelId);

    Set<Vehicle> findAllByFuelTypeId(Long fuelTypeId);

    Set<Vehicle> findAllByGearboxTypeId(Long gearboxTypeId);

    Set<Vehicle> findAllByVehicleClassId(Long vehicleClassId);

}
