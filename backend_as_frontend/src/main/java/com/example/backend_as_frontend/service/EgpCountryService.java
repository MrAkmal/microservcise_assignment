package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.dto.EgpCountryCreateDTO;
import com.example.backend_as_frontend.dto.EgpCountryDTO;
import com.example.backend_as_frontend.dto.EgpCountryUpdateDTO;
import com.example.backend_as_frontend.dto.FinancialYearCreateDTO;
import com.example.backend_as_frontend.entity.CountryBase;
import com.example.backend_as_frontend.entity.FinancialYear;
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
public class EgpCountryService {

    private final WebClient webClient;
    private final String baseURI = "http://localhost:9595/v1/key_word_base/egp_country_server";

    @Autowired
    public EgpCountryService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<EgpCountryDTO> getAll() {

        Mono<List<EgpCountryDTO>> egpCountryFlux = webClient.get()
                .uri(baseURI)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(EgpCountryDTO.class)
                .collectList();

        List<EgpCountryDTO> block = egpCountryFlux.block();

        return block;

    }


    public void save(EgpCountryCreateDTO egpCountryCreateDTO) {

        Mono<EgpCountryDTO> mono = webClient.post()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(egpCountryCreateDTO), EgpCountryDTO.class)
                .retrieve()
                .bodyToMono(EgpCountryDTO.class);

        System.out.println("mono = " + mono.block());
    }

    public EgpCountryDTO get(Integer id) {
        Mono<EgpCountryDTO> egpCountryMono = webClient.get()
                .uri(baseURI + "/" + id)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(EgpCountryDTO.class);

        return egpCountryMono.block();
    }

    public void update(EgpCountryUpdateDTO egpCountryUpdateDTO) {

        Mono<EgpCountryDTO> mono = webClient.put()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(egpCountryUpdateDTO), EgpCountryDTO.class)
                .retrieve()
                .bodyToMono(EgpCountryDTO.class);

        System.out.println("mono = " + mono.block());
    }

    public void delete(Integer id) {

        Mono<Void> mono = webClient.delete()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(Void.class);
        System.out.println("mono = " + mono.block());
    }
}
