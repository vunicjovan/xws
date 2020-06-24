package com.uns.ftn.messageservice.configuration;

import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class FeignClientConf {
    @Bean
    public Client feignClient(SSLContext sslContext)
    {
        Client trustSSLSockets = new Client.Default(sslContext.getSocketFactory(), null);
        return trustSSLSockets;
    }
}
