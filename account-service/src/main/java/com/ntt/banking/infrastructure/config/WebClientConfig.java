package com.ntt.banking.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${services.customer.url:http://localhost:8081}")
    private String customerServiceUrl;

    @Bean
    public WebClient customerWebClient(WebClient.Builder builder) {
        return builder.baseUrl(customerServiceUrl).build();
    }
}
