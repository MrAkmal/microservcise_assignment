package com.example.procurement_method_service.procurementMethod;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AppConfig {

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().build();
    }

}
