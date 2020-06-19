package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findAllByDeleted(Boolean deleted);
}
