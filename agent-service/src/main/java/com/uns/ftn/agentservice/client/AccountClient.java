package com.uns.ftn.agentservice.client;

import com.uns.ftn.agentservice.conf.FeignClientConf;
import com.uns.ftn.agentservice.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service", configuration = FeignClientConf.class, url = "https://localhost:8089/account")
public interface AccountClient {

    @GetMapping("/{id}")
    UserResponseDTO getUser(@PathVariable Long id);
}
