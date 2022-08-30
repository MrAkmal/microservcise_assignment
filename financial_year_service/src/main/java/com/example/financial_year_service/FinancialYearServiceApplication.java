package com.example.financial_year_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@OpenAPIDefinition
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class FinancialYearServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialYearServiceApplication.class, args);
    }

}
