package com.uns.ftn.rentingservice;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableRabbit
@EnableFeignClients
public class RentingServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(RentingServiceApplication.class);

	@RequestMapping("/health")
	public String home() {
		return "Renting Service alive.";
	}

	public static void main(String[] args) {
		SpringApplication.run(RentingServiceApplication.class, args);
	}

	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs(SSLContext sslContext) throws NoSuchAlgorithmException, URISyntaxException, KeyStoreException, KeyManagementException {
		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();

		args.setSSLContext(sslContext);
		return args;
	}

	@Bean
	public SSLContext sslContext() throws URISyntaxException, KeyStoreException, KeyManagementException, IOException,
			CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
		URL keystoreResource = RentingServiceApplication.class.getResource("/renting.keystore.p12");
		URL truststoreResource = RentingServiceApplication.class.getResource("/renting.truststore.p12");
		try {
			String keystorePath = keystoreResource.toURI().getPath();
			String truststorePath = truststoreResource.toURI().getPath();
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			LOGGER.info("Loading keystore from: " + keystorePath);
			keyStore.load(new FileInputStream(new File(keystorePath)), "password".toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keyStore, "password".toCharArray());

			KeyStore trustStore = KeyStore.getInstance("PKCS12");
			LOGGER.info("Loading truststore from: " + truststorePath);
			trustStore.load(new FileInputStream(new File(truststorePath)), "password".toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(trustStore);

			SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
			sslcontext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			return sslcontext;
		} catch (Exception e) {
			LOGGER.error("Error occurred during configuration of SSLContext", e);
			throw new IllegalStateException("Error while configuring SSLContext", e);
		}
	}

}
