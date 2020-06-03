package com.uns.ftn.viewservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/owner/{id}")
    String getOwnerName(@PathVariable Long id);

}
