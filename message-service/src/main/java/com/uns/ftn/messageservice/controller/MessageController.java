package com.uns.ftn.messageservice.controller;

import com.uns.ftn.messageservice.dto.MessageDTO;
import com.uns.ftn.messageservice.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

   // @Autowired
   // private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @PreAuthorize("hasAuthority('GET_MESSAGES')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        logger.debug("Get chat for user with id {}", id);
        return new ResponseEntity<>(messageService.getChat(id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getMessagesByUser(@PathVariable("id") Long id) {
        return null;
    }

    @PreAuthorize("hasAuthority('SEND_MESSAGE')")
    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO) {
        logger.debug("Send message from {}", messageDTO.getSenderId());
        return new ResponseEntity<>(messageService.saveMessage(messageDTO), HttpStatus.OK);
    }

    // renting-service: approving rent request
    @PostMapping("chat/{senderId}/{receiverId}")
    public ResponseEntity<?> sendMessage(@PathVariable Long senderId, @PathVariable Long receiverId) {
        logger.debug("Create init message for sender {} and receiver {}", senderId, receiverId);
        return new ResponseEntity<>(messageService.createChat(senderId, receiverId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
    }

    @MessageMapping("/send")
    public void sendMessageSocket(@Payload MessageDTO messageDTO) {
  //      simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageDTO.getReceiverId(), messageDTO);
    }

}
