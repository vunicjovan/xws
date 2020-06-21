package com.uns.ftn.agent.conf;

import com.uns.ftn.agent.client.AdvertisementClient;
import com.uns.ftn.agent.client.CatalogClient;
import com.uns.ftn.agent.client.MessageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ClientsConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("rs.ac.uns.ftn.catalog");
        return marshaller;
    }

    @Bean
    public CatalogClient catalogClient(Jaxb2Marshaller marshaller) {
        CatalogClient client = new CatalogClient();
        client.setDefaultUri("http://localhost:8083/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public AdvertisementClient advertisementClient(Jaxb2Marshaller marshaller) {
        AdvertisementClient client = new AdvertisementClient();
        client.setDefaultUri("http://localhost:8081/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public MessageClient messageClient(Jaxb2Marshaller marshaller) {
        MessageClient client = new MessageClient();
        client.setDefaultUri("http://localhost:8085/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
