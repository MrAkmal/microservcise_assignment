package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.entity.CountryBase;
import com.example.backend_as_frontend.entity.FinancialYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.backend_as_frontend.utils.Utils.getToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class CountryBaseService {

    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595/v1/key_word_base/country_base_server";


    @Autowired
    public CountryBaseService(WebClient webClient) {
        this.webClient = webClient;
    }


    public List<CountryBase> getAll() {

        Mono<List<CountryBase>> financialYearFlux = webClient.get()
                .uri(baseURI)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(CountryBase.class)
                .collectList();

        List<CountryBase> block = financialYearFlux.block();

        return block;

    }

    public int getCountryId(String countryName) {
        Mono<CountryBase> country = webClient.get()
                .uri(baseURI + "/name/" + countryName)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(CountryBase.class);

        CountryBase block = country.block();

        if (block != null) {
            return block.getId();
        }
        return 0;

    }
}
