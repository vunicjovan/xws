package com.uns.ftn.catalogservice.repository;

import com.uns.ftn.catalogservice.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAllByDeleted(Boolean deleted);
    Brand findByName(String name);
}
