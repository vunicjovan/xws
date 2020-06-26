package com.uns.ftn.agentservice.client;

import com.uns.ftn.agentservice.dto.CheckResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service")
public interface CatalogClient {

    @GetMapping("/resourceCheck/{resources}")
    CheckResponseDTO checkIfResourcesExist(@PathVariable String resources);

}
