package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.RentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingRequestRepository extends JpaRepository<RentingRequest, Long> {
}
