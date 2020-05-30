package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
