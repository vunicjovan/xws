package com.uns.ftn.agentservice.controller;

import com.uns.ftn.agentservice.dto.CommDTO;
import com.uns.ftn.agentservice.service.CommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommController {

    @Autowired
    private CommService commService;

    @PostMapping("/")
    public ResponseEntity<?> postComment(@RequestBody CommDTO commDTO) {
        return new ResponseEntity<>(commService.postComment(commDTO), HttpStatus.OK);
    }
}
