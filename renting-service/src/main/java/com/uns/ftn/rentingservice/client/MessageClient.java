package com.uns.ftn.rentingservice.client;

import com.uns.ftn.rentingservice.conf.FeignClientConf;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "message-service", configuration = FeignClientConf.class, url = "https://localhost:8089/message")
public interface MessageClient {

    @PostMapping("chat/{senderId}/{receiverId}")
    boolean createChat(@PathVariable Long senderId, @PathVariable Long receiverId);

}
