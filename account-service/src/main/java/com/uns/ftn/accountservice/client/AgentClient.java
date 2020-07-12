package com.uns.ftn.accountservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("agent-service")
public interface AgentClient {

    @GetMapping("/ad/check/{id}")
    Boolean userHasAds(@PathVariable Long id);
}
