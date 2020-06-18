package com.uns.ftn.agent.conf;

import com.uns.ftn.agent.client.RentingRequestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class RentingRequestClientConfig {

    @Bean
    public Jaxb2Marshaller requestMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("rs.ac.uns.ftn.catalog");
        return marshaller;
    }

    @Bean
    public RentingRequestClient rentingRequestClient(Jaxb2Marshaller marshaller) {
        RentingRequestClient client = new RentingRequestClient();
        client.setDefaultUri("http://localhost:8086/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
