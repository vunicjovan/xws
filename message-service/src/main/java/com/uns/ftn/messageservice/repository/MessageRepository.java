package com.uns.ftn.messageservice.repository;

import com.uns.ftn.messageservice.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderIdOrReceiverId(Long senderId, Long receiverId);
    List<Message> findAllBySenderId(Long id);
    List<Message> findAllByReceiverId(Long id);
}
