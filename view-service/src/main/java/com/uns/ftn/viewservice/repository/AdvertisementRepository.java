package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
