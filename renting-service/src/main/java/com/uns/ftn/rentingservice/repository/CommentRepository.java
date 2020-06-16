package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
