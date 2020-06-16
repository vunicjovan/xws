package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommRepository extends JpaRepository<Comment, Long> {
}
