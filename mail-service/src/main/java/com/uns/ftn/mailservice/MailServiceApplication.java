package com.uns.ftn.mailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableEurekaClient
public class MailServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Mail Service alive.";
	}

	public static void main(String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}

}
