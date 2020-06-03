package com.uns.ftn.searchservice.repository;

import com.uns.ftn.searchservice.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
