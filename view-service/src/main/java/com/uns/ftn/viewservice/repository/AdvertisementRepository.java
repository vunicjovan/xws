package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Set<Advertisement> findAllByOwnerId(Long id);
}
