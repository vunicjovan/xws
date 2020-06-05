package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
