package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.dto.*;
import com.example.backend_as_frontend.entity.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.backend_as_frontend.utils.Utils.getToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class PaymentConfigurationService {

    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595/v1/payment/payment_configuration";

    private final PaymentBaseService paymentBaseService;

    @Autowired
    public PaymentConfigurationService(WebClient webClient, PaymentBaseService paymentBaseService) {
        this.webClient = webClient;
        this.paymentBaseService = paymentBaseService;
    }

    public void save(PaymentConfigCreateDTO paymentConfig) {


        Mono<PaymentConfigCreateDTO> mono = webClient.post()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(paymentConfig), PaymentConfigCreateDTO.class)
                .retrieve()
                .bodyToMono(PaymentConfigCreateDTO.class);

        PaymentConfigCreateDTO block = mono.block();
        System.out.println("mono = " + block);

        if (block != null) {
            for (TypeCreateDTO type : paymentConfig.getTypes()) {
                type.setPaymentConfigurationId(block.getId());
            }
            paymentBaseService.saveAll(paymentConfig.getTypes());
        }

    }


    public List<PaymentConfigurationDTO> getAll() {
        Mono<List<PaymentConfigurationDTO>> entity = webClient.get()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(PaymentConfigurationDTO.class)
                .collectList();

        List<PaymentConfigurationDTO> block = entity.block();
        return block;
    }

    public void delete(Integer id) {

        if (id != null) {

            Mono<Void> mono = webClient.delete()
                    .uri(baseURI + "/" + id)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(AUTHORIZATION, getToken())
                    .retrieve()
                    .bodyToMono(Void.class);
            System.out.println("mono = " + mono.block());
        }
    }
}
