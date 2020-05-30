package com.uns.ftn.catalogservice.repository;

import com.uns.ftn.catalogservice.domain.FuelType;
import com.uns.ftn.catalogservice.domain.GearboxType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GearboxTypeRepository extends JpaRepository<GearboxType, Long> {
    List<GearboxType> findAllByDeleted(Boolean deleted);
    GearboxType findByName(String name);
}
