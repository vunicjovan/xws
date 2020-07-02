package com.uns.ftn.rentingservice.client;

import com.uns.ftn.rentingservice.dto.CommentClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("agent-service")
public interface CommentClient {

    @GetMapping("/ad/comment/{id}")
    CommentClientResponseDTO getComment(@PathVariable Long id);

    @GetMapping("/ad/generateDebt/{adId}/{numberOfDays}/{kmTraveled}")
    double generateDebtPrice(@PathVariable Long adId, @PathVariable int numberOfDays,
                             @PathVariable int kmTraveled);
}
