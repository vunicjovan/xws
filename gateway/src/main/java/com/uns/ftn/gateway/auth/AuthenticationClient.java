package com.uns.ftn.gateway.auth;

import com.uns.ftn.gateway.FeignClientConf;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "account-service", configuration = FeignClientConf.class, url = "https://localhost:8080")
public interface AuthenticationClient {

    @GetMapping("/verify")
    List<String> verify(@RequestParam(value = "token") String token);

}
