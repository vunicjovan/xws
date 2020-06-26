package com.uns.ftn.agent.conf;

import com.uns.ftn.agent.client.RentingRequestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

@Configuration
public class RentingRequestClientConfig {

    @Bean
    public Jaxb2Marshaller requestMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("rs.ac.uns.ftn.catalog");
        return marshaller;
    }

    @Bean
    public RentingRequestClient rentingRequestClient(Jaxb2Marshaller marshaller, KeyManagerFactory keyManagerFactory,
                                                     TrustManagerFactory trustManagerFactory) {
        RentingRequestClient client = new RentingRequestClient();
        client.setDefaultUri("http://localhost:8089/rent/ws");
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
