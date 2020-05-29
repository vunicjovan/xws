package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
