package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.AndroidLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AndroidRepository extends JpaRepository<AndroidLocation, Long> {

    AndroidLocation findByToken(String token);

    AndroidLocation findByVehicle_Id(Long id);

}
