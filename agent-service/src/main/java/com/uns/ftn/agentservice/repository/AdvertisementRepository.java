package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByOwnerId(Long ownerId);

    List<Advertisement> findByOwnerIdAndAndDeleted(Long ownerId, Boolean deleted);

}
