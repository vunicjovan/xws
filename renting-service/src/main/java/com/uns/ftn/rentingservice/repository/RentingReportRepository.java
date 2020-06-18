package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.RentingReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingReportRepository extends JpaRepository<RentingReport, Long> {
}
