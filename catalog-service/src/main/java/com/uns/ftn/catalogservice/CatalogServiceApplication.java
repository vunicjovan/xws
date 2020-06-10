package com.uns.ftn.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableEurekaClient
@RestController
@EnableFeignClients
public class CatalogServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Catalog service alive.";
	}


	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
		System.out.println("______Catalog Service Started______");
	}

//	@Bean
//	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException, URISyntaxException {
//		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
//		URL trustStoreResource = CatalogServiceApplication.class.getResource("/renta-keystore.p12");
//		String path = trustStoreResource.toURI().getPath();
//		System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
//		System.setProperty("javax.net.ssl.keyStore", path);
//		System.setProperty("javax.net.ssl.keyStorePassword", "password");
//		System.setProperty("javax.net.ssl.trustStore", path);
//		System.setProperty("javax.net.ssl.trustStorePassword", "password");
//		EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
//		builder.withClientName("catalog-service");
//		builder.withSystemSSLConfiguration();
//		builder.withMaxTotalConnections(10);
//		builder.withMaxConnectionsPerHost(10);
//		args.setEurekaJerseyClient(builder.build());
//		return args;
//	}

}
