package com.example.springappdemo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class SpringBeansConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate(); // new RestTemplateBuilder().build();
    }

//    @Bean
//    public FilterRegistrationBean<DelegatingFilterProxy> authFilterRegistration() {
//        FilterRegistrationBean<DelegatingFilterProxy> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new DelegatingFilterProxy("customAuthFilter"));
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }
}

