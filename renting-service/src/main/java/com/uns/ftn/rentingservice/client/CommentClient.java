package com.uns.ftn.rentingservice.client;

import com.uns.ftn.rentingservice.conf.FeignClientConf;
import com.uns.ftn.rentingservice.dto.CommentClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "agent-service", configuration = FeignClientConf.class, url = "https://localhost:8089/agent")
public interface CommentClient {
    @GetMapping("/ad/comment/{id}")
    CommentClientResponseDTO getComment(@PathVariable Long id);
}
