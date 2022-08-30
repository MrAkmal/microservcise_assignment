package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.dto.FinancialYearCreateDTO;
import com.example.backend_as_frontend.dto.PaymentTypeCreateDTO;
import com.example.backend_as_frontend.entity.FinancialYear;
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
public class PaymentTypeService {

    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595/v1/payment/payment_type";

    @Autowired
    public PaymentTypeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PaymentType> getAll() {
        Mono<List<PaymentType>> entity = webClient.get()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(PaymentType.class)
                .collectList();

        List<PaymentType> block = entity.block();
        return block;
    }

    public void save(PaymentTypeCreateDTO paymentTypeCreateDTO) {

        Mono<PaymentType> mono = webClient.post()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(paymentTypeCreateDTO), PaymentTypeCreateDTO.class)
                .retrieve()
                .bodyToMono(PaymentType.class);

        System.out.println("mono = " + mono.block());
    }

    public PaymentType get(Integer id) {
        Mono<PaymentType> entity = webClient.get()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(PaymentType.class);

        PaymentType block = entity.block();
        return block;
    }

    public void update(PaymentType dto) {
        Mono<PaymentType> mono = webClient.put()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(dto), PaymentTypeCreateDTO.class)
                .retrieve()
                .bodyToMono(PaymentType.class);

        System.out.println("mono = " + mono.block());
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
