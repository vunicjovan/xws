package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.RentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RentingRequestRepository extends JpaRepository<RentingRequest, Long> {
    List<RentingRequest> findAllBySenderId(Long senderId);
}
