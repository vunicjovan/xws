package com.uns.ftn.androidservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableEurekaClient
@RestController
public class AndroidServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Android service alive.";
	}

	public static void main(String[] args) {
		SpringApplication.run(AndroidServiceApplication.class, args);
		System.out.println("_______Android Service Started_______");
	}
}
