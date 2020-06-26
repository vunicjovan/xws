package com.uns.ftn.catalogservice;

import com.netflix.discovery.DiscoveryClient;
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

	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs(SSLContext sslContext) throws NoSuchAlgorithmException, URISyntaxException, KeyStoreException, KeyManagementException {
		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();

		args.setSSLContext(sslContext);
		return args;
	}

	@Bean
	public SSLContext sslContext() throws URISyntaxException, KeyStoreException, KeyManagementException, IOException,
			CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
		URL keystoreResource =  CatalogServiceApplication.class.getResource("/catalog.keystore.p12");
		URL truststoreResource = CatalogServiceApplication.class.getResource("/catalog.truststore.p12");
		String keystorePath = keystoreResource.toURI().getPath();
		String truststorePath = truststoreResource.toURI().getPath();
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(new FileInputStream(new File(keystorePath)), "password".toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(keyStore, "password".toCharArray());

		KeyStore trustStore = KeyStore.getInstance("PKCS12");
		trustStore.load(new FileInputStream(new File(truststorePath)), "password".toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(trustStore);

		SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
		sslcontext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return sslcontext;
	}

}
