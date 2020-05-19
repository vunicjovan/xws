package com.uns.ftn.agentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableEurekaClient
@RestController
public class AgentServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Agent service alive.";
	}

	public static void main(String[] args) {
		SpringApplication.run(AgentServiceApplication.class, args);
		System.out.println("_______Agent Service Started_______");
	}

}
