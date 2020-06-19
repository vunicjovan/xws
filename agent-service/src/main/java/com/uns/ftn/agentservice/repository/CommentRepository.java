package com.uns.ftn.agentservice.repository;

import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByAllowed(Boolean allowed);

    List<Comment> getAllByAdvertisementAndAllowed(Advertisement advertisement, Boolean allowed);
}
