package com.uns.ftn.rentingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("account-service")
public interface AccountClient {

    @PutMapping("/cancelation/{id}")
    String incrementCancelation(@PathVariable Long id);

}
