package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByOwnerId(Long ownerId);

}
