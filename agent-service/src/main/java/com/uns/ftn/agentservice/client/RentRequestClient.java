package com.uns.ftn.agentservice.client;

import com.uns.ftn.agentservice.conf.FeignClientConf;
import com.uns.ftn.agentservice.dto.CommDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "renting-service", configuration = FeignClientConf.class, url = "https://localhost:8089/rent")
public interface RentRequestClient {

    @GetMapping("/request/comment/{userId}/{advertisementId}")
    CommDTO checkCommentPostingPermission(@PathVariable Long userId,
                                          @PathVariable Long advertisementId);
}
