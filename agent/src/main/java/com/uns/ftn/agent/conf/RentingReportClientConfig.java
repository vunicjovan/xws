package com.uns.ftn.agent.conf;

import com.uns.ftn.agent.client.RentingReportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class RentingReportClientConfig {

    @Bean
    public Jaxb2Marshaller reportMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("rs.ac.uns.ftn.catalog");
        return marshaller;
    }

    @Bean
    public RentingReportClient rentingReportClient(Jaxb2Marshaller marshaller) {
        RentingReportClient client = new RentingReportClient();
        client.setDefaultUri("http://localhost:8086/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
