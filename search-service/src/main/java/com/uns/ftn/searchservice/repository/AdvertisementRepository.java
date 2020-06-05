package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdvertisementRepository extends
        JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {
}
