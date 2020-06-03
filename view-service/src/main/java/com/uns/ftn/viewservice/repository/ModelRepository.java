package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
