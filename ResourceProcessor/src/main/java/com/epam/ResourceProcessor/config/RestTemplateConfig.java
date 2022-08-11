package com.epam.ResourceProcessor.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @LoadBalanced
    @Bean(name = "restTemplate")
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
