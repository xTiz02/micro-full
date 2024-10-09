package org.prd.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Bean
    @LoadBalanced //para que sepa que es un cliente que va a hacer peticiones a otros servicios que estan en el eureka
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
