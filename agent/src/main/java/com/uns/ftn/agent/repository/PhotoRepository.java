package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
