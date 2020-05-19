package com.uns.ftn.viewservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class ViewServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Hello world";
	}

	public static void main(String[] args) {
		SpringApplication.run(ViewServiceApplication.class, args);
	}

}
