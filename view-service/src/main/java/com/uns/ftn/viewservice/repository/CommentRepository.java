package com.uns.ftn.viewservice.repository;

import com.uns.ftn.viewservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
