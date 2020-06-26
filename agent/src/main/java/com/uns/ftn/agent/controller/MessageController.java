package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.dto.MessageDTO;
import com.uns.ftn.agent.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getChat() {
        logger.debug("Get chat");
        return new ResponseEntity<>(messageService.getChat(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO) {
        logger.debug("Send message");
        return new ResponseEntity<>(messageService.sendMessage(messageDTO), HttpStatus.OK);
    }

}
