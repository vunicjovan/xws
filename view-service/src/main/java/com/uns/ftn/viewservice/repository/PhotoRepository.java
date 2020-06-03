package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
