package com.uns.ftn.catalogservice.repository;

import com.uns.ftn.catalogservice.domain.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleClassRepository extends JpaRepository<VehicleClass, Long> {
    VehicleClass findByName(String name);
    List<VehicleClass> findAllByDeleted(Boolean deleted);
}
