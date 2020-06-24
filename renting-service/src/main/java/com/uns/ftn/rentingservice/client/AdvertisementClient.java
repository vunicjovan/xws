package com.uns.ftn.rentingservice.client;

import com.uns.ftn.rentingservice.conf.FeignClientConf;
import com.uns.ftn.rentingservice.dto.AdvertClientResponseDTO;
import com.uns.ftn.rentingservice.dto.CommentClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "view-service", configuration = FeignClientConf.class, url = "https://localhost:8089/view")
public interface AdvertisementClient {

    @GetMapping("client/{id}")
    AdvertClientResponseDTO getAd(@PathVariable Long id);

}
