package com.uns.ftn.messageservice;

import com.uns.ftn.messageservice.configuration.FeignClientConf;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service", configuration = FeignClientConf.class, url = "https://localhost:8089/account")
public interface AccountClient {

    @GetMapping("/owner/{id}")
    String getOwnerName(@PathVariable Long id);

}
