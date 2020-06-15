package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
