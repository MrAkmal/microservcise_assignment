package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.dto.PaymentBaseDTO;
import com.example.backend_as_frontend.dto.PaymentConfigCreateDTO;
import com.example.backend_as_frontend.dto.TypeCreateDTO;
import com.example.backend_as_frontend.entity.ProcurementMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.backend_as_frontend.utils.Utils.getToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class PaymentBaseService {

    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595/v1/payment/payment_base";

    @Autowired
    public PaymentBaseService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void saveAll(List<TypeCreateDTO> types) {

        Mono<List<PaymentBaseDTO>> mono = webClient.post()
                .uri(baseURI + "/save-all")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(types), TypeCreateDTO.class)
                .retrieve()
                .bodyToFlux(PaymentBaseDTO.class)
                .collectList();

        System.out.println("mono = " + mono.block());
    }
}
