package com.uns.ftn.catalogservice.repository;

import com.uns.ftn.catalogservice.domain.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    List<FuelType> findAllByDeleted(Boolean deleted);

    FuelType findByName(String name);
}
