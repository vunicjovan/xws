package com.uns.ftn.agentservice.client;

import com.uns.ftn.agentservice.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/{id}")
    UserResponseDTO getUser(@PathVariable Long id);

    @GetMapping("/{id}/ad/count")
    int getNumberOfPostedAds(@PathVariable Long id);
}
