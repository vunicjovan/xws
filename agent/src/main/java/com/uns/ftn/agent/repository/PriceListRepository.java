package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
}
