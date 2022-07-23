package com.epam.GatewayService.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r.path("/resources/**")
                        .uri("lb://resource-service"))
                .route(r -> r.path("/songs/**")
                        .uri("lb://song-service"))
//                .route(r -> r.path("/"))
                .build();
    }
}
