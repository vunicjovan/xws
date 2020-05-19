package com.uns.ftn.messageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@RestController
@EnableEurekaClient
public class MessageServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Message Service alive.";
	}

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}

}
