package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
