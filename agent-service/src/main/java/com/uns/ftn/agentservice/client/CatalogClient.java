package com.uns.ftn.agentservice.client;

import com.uns.ftn.agentservice.conf.FeignClientConf;
import com.uns.ftn.agentservice.dto.CheckResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", configuration = FeignClientConf.class, url = "https://localhost:8089/catalog")
public interface CatalogClient {

    @GetMapping("/resourceCheck/{resources}")
    CheckResponseDTO checkIfResourcesExist(@PathVariable String resources);

}
