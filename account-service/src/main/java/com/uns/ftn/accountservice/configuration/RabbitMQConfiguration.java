package com.uns.ftn.accountservice.configuration;

import com.uns.ftn.accountservice.AccountServiceApplication;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

    @Value("${fanout.exchange}")
    private String fanoutExchangeName;

    private final Environment env;

    public RabbitMQConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(fanoutExchangeName);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

//    @Bean
//    public RabbitConnectionFactoryBean connectionFactoryBean() throws IOException, URISyntaxException {
//        RabbitConnectionFactoryBean connectionFactoryBean = new RabbitConnectionFactoryBean();
//        connectionFactoryBean.setHost("localhost");
//        connectionFactoryBean.setPort(new Integer("5672"));
//
//        connectionFactoryBean.setUseSSL(true);
//        connectionFactoryBean.setSslAlgorithm("TLSv1.2");
//
//        URL keystoreResource = AccountServiceApplication.class.getResource("/account.keystore.p12");
//        URL truststoreResource = AccountServiceApplication.class.getResource("/account.truststore.p12");
//        String keystorePath = keystoreResource.toURI().getPath();
//        String truststorePath = truststoreResource.toURI().getPath();
//
//        // This information should be stored safely !!!
//        connectionFactoryBean.setKeyStore(keystorePath);
//        connectionFactoryBean.setKeyStorePassphrase("password");
//        connectionFactoryBean.setTrustStore(truststorePath);
//        connectionFactoryBean.setTrustStorePassphrase("password");
//
//
//        return connectionFactoryBean;
//    }
//
//    @Bean(name = "GEO_RABBIT_CONNECTION")
//    public ConnectionFactory connectionFactory(RabbitConnectionFactoryBean connectionFactoryBean) throws Exception {
//        return new CachingConnectionFactory(connectionFactoryBean.getObject());
//    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
