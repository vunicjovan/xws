package com.uns.ftn.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;

@SpringBootApplication
public class AgentApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentApplication.class);

	@Value("${renta.ws.key-store}")
	private Resource keyStore;

	@Value("${renta.ws.key-store-password}")
	private String keyStorePassword;

	@Value("${renta.ws.trust-store}")
	private Resource trustStore;

	@Value("${renta.ws.trust-store-password}")
	private String trustStorePassword;

	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
	}


	@Bean
	public KeyManagerFactory keyManagerFactory() {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(keyStore.getInputStream(), keyStorePassword.toCharArray());
			LOGGER.info("Loaded keystore: " + keyStore.getURI().toString());
			try {
				keyStore.getInputStream().close();
			} catch (IOException e) {

			}

			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(ks, keyStorePassword.toCharArray());

			return keyManagerFactory;
		} catch (Exception e) {
			LOGGER.error("Error occurred during initializing KeyManagerFactory", e);
			throw new IllegalStateException("Error while configuring KeyManagerFactory", e);
		}
	}

	@Bean
	public TrustManagerFactory trustManagerFactory() {
		try {
			KeyStore ts = KeyStore.getInstance("PKCS12");
			ts.load(trustStore.getInputStream(), trustStorePassword.toCharArray());
			LOGGER.info("Loaded truststore: " + trustStore.getURI().toString());
			try {
				trustStore.getInputStream().close();
			} catch (IOException e) {

			}

			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(ts);

			return trustManagerFactory;
		} catch (Exception e) {
			LOGGER.error("Error occurred during initializing TrustManagerFactory", e);
			throw new IllegalStateException("Error while configuring TrustManagerFactory", e);
		}
	}
}
