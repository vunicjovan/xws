package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.GearboxType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GearboxTypeRepository extends JpaRepository<GearboxType, Long> {

    List<GearboxType> findAllByDeleted(Boolean deleted);
}
