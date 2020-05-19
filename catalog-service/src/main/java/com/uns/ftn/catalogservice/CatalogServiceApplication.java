package com.uns.ftn.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableEurekaClient
@RestController
public class CatalogServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Catalog service alive.";
	}


	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
		System.out.println("______Catalog Service Started______");
	}

}
