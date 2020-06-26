package com.uns.ftn.rentingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("message-service")
public interface MessageClient {

    @PostMapping("chat/{senderId}/{receiverId}")
    boolean createChat(@PathVariable Long senderId, @PathVariable Long receiverId);

}
