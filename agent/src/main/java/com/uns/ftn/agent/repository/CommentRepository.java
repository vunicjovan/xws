package com.uns.ftn.agent.repository;

import com.uns.ftn.agent.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
