package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findAllByDeleted(Boolean deleted);
}
