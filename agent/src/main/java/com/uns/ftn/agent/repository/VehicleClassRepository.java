package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleClassRepository extends JpaRepository<VehicleClass, Long> {

    List<VehicleClass> findAllByDeleted(Boolean deleted);
}
