package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByAdvertisement_Id(Long id);
}
