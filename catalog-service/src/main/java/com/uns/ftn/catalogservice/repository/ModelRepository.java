package com.uns.ftn.catalogservice.repository;

import com.uns.ftn.catalogservice.domain.Brand;
import com.uns.ftn.catalogservice.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findByName(String name);
    Model findByNameAndBrand(String name, Brand brand);
    Model findByIdAndBrand(Long id, Brand brand);
    List<Model> findAllByDeleted(Boolean deleted);
}
