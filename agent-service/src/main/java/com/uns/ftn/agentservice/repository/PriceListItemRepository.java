package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {
}
