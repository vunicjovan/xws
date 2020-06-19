package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    List<FuelType> findAllByDeleted(Boolean deleted);
}
