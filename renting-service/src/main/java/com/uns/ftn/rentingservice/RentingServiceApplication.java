package com.uns.ftn.rentingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@RestController
@EnableEurekaClient
public class RentingServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Renting Service alive.";
	}

	public static void main(String[] args) {
		SpringApplication.run(RentingServiceApplication.class, args);
	}

}
