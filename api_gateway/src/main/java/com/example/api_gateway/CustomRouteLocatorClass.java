package com.example.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRouteLocatorClass {


    private final AuthenticationFilter filter;

    public CustomRouteLocatorClass(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("financial-server", r -> r.path("/v1/financial_year/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://financial-server"))

                .route("procurement-nature-server", r -> r.path("/v1/procurement_nature/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://procurement-nature-server"))

                .route("procurement-method-server", r -> r.path("/v1/procurement_method/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://procurement-method-server"))


                .route("payment-server", r -> r.path("/v1/payment/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://payment-server"))


                .route("keyword-base-server", r -> r.path("/v1/key_word_base/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://keyword-base-server"))


                .route("menu-server", r -> r.path("/v1/menu/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://menu-server"))


                .route("resource-server", r -> r.path("/**")
                        .uri("lb://resource-server"))
                .build();
    }


}
