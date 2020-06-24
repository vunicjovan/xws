package com.uns.ftn.catalogservice.client;

import com.uns.ftn.catalogservice.conf.FeignClientConf;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "agent-service", configuration = FeignClientConf.class, url = "https://localhost:8089/agent")
public interface VehicleClient {

    @GetMapping("/ad/modelCheck/{id}")
    Boolean checkIfModelIsTaken(@PathVariable Long id);

    @GetMapping("/ad/gearboxCheck/{id}")
    Boolean checkIfGearboxIsTaken(@PathVariable Long id);

    @GetMapping("/ad/fuelCheck/{id}")
    Boolean checkIfFuelIsTaken(@PathVariable Long id);

    @GetMapping("/ad/classCheck/{id}")
    Boolean checkIfClassIsTaken(@PathVariable Long id);

}
