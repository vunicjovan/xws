package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.RentingReport;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingReportRepository extends JpaRepository<RentingReport, Long> {
    RentingReport findByAdvertisementAndRentingRequest(Advertisement advertisement, RentingRequest rentingRequest);
}
