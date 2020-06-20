package com.uns.ftn.rentingservice.client;

import com.uns.ftn.rentingservice.dto.AdvertClientResponseDTO;
import com.uns.ftn.rentingservice.dto.CommentClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("view-service")
public interface AdvertisementClient {

    @GetMapping("client/{id}")
    AdvertClientResponseDTO getAd(@PathVariable Long id);

    @GetMapping("comment/{id}")
    CommentClientResponseDTO getComment(@PathVariable Long id);

}
