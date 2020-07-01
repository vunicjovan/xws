package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {
}
