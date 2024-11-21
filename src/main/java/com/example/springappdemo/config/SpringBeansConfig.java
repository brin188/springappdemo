package com.example.springappdemo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringBeansConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate(); // new RestTemplateBuilder().build();
    }
}

