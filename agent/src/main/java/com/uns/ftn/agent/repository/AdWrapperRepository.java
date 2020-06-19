package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.AdWrapper;
import com.uns.ftn.agent.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdWrapperRepository extends JpaRepository<AdWrapper, Long> {

    AdWrapper findByAdvertisementId (Long id);
}
