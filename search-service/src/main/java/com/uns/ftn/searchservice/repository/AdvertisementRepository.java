package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
