package com.uns.ftn.gateway;

import com.netflix.discovery.DiscoveryClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
@EnableEurekaClient
@EnableZuulProxy
@EnableFeignClients
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
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
		URL keystoreResource =  GatewayApplication.class.getResource("/zuul.keystore.p12");
		URL truststoreResource = GatewayApplication.class.getResource("/zuul.truststore.p12");
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

	@Bean
	public CloseableHttpClient httpClient(SSLContext sslContext) {
		try {
			SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(sslContext,
					new String[]{"TLSv1.2"},
					null, (hostname, sslSession) -> true);

			return HttpClients.custom().setSSLSocketFactory(sslFactory).build();
		} catch (Exception e) {
			throw new IllegalStateException("Error while configuring http client", e);
		}
	}

  @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings (CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://localhost:8090")
						.allowCredentials(true)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
			}
		};
	}

}
