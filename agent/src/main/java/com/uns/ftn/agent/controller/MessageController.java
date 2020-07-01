package com.uns.ftn.agent.controller;

import com.uns.ftn.agent.dto.MessageDTO;
import com.uns.ftn.agent.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getChat() {
        return new ResponseEntity<>(messageService.getChat(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO) {
        return new ResponseEntity<>(messageService.sendMessage(messageDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> sendMessage(@PathVariable("id") Long id) {
        return new ResponseEntity<>(messageService.deleteMessage(id), HttpStatus.OK);
    }

}
