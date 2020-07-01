package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    PriceList findByOwnerId(Long id);
}
