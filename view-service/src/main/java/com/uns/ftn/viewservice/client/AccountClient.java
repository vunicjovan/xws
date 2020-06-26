package com.uns.ftn.viewservice.client;

import com.uns.ftn.viewservice.conf.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service", configuration = FeignClientConfig.class, url = "https://localhost:8089/account")
public interface AccountClient {

    @GetMapping("/owner/{id}")
    String getOwnerName(@PathVariable Long id);

}
