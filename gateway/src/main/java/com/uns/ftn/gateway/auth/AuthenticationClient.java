package com.uns.ftn.gateway.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("account-service")
public interface AuthenticationClient {

    @GetMapping("/verify")
    List<String> verify(@RequestParam(value = "token") String token);

}
