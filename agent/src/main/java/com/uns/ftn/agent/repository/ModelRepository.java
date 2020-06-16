package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
