package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
