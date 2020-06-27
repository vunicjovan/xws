package com.uns.ftn.agent.conf;

import com.uns.ftn.agent.client.AdvertisementClient;
import com.uns.ftn.agent.client.CatalogClient;
import com.uns.ftn.agent.client.MessageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

@Configuration
public class ClientsConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("rs.ac.uns.ftn.catalog");
        return marshaller;
    }

    @Bean
    public CatalogClient catalogClient(Jaxb2Marshaller marshaller, KeyManagerFactory keyManagerFactory,
                                       TrustManagerFactory trustManagerFactory) {
        CatalogClient client = new CatalogClient();
        client.setDefaultUri("https://localhost:8089/catalog/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

        messageSender.setHostnameVerifier((hostname, sslSession) -> {
            if(hostname.equals("localhost")) {
                return true;
            }

            return false;
        });

        client.setMessageSender(messageSender);

        return client;
    }

    @Bean
    public AdvertisementClient advertisementClient(Jaxb2Marshaller marshaller, KeyManagerFactory keyManagerFactory,
                                                   TrustManagerFactory trustManagerFactory) {
        AdvertisementClient client = new AdvertisementClient();
        client.setDefaultUri("https://localhost:8089/agent/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

        messageSender.setHostnameVerifier((hostname, sslSession) -> {
            if(hostname.equals("localhost")) {
                return true;
            }

            return false;
        });

        client.setMessageSender(messageSender);
        return client;
    }

    @Bean
    public MessageClient messageClient(Jaxb2Marshaller marshaller, KeyManagerFactory keyManagerFactory,
                                       TrustManagerFactory trustManagerFactory) {
        MessageClient client = new MessageClient();
        client.setDefaultUri("https://localhost:8089/message/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

        messageSender.setHostnameVerifier((hostname, sslSession) -> {
            if(hostname.equals("localhost")) {
                return true;
            }

            return false;
        });

        client.setMessageSender(messageSender);
        return client;
    }
}
