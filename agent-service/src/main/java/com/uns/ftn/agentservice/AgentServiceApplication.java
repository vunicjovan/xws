package com.uns.ftn.agentservice;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableEurekaClient
@RestController
@EnableRabbit
@EnableFeignClients
public class AgentServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Agent service alive.";
	}

	public static void main(String[] args) {
		SpringApplication.run(AgentServiceApplication.class, args);
		System.out.println("_______Agent Service Started_______");
	}

//	@Bean
//	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException, URISyntaxException {
//		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
//		URL trustStoreResource = AgentServiceApplication.class.getResource("/renta-keystore.p12");
//		String path = trustStoreResource.toURI().getPath();
//		System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
//		System.setProperty("javax.net.ssl.keyStore", path);
//		System.setProperty("javax.net.ssl.keyStorePassword", "password");
//		System.setProperty("javax.net.ssl.trustStore", path);
//		System.setProperty("javax.net.ssl.trustStorePassword", "password");
//		EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
//		builder.withClientName("agent-service");
//		builder.withSystemSSLConfiguration();
//		builder.withMaxTotalConnections(10);
//		builder.withMaxConnectionsPerHost(10);
//		args.setEurekaJerseyClient(builder.build());
//		return args;
//	}

}
