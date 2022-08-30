package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.entity.ProcurementMethod;
import com.example.backend_as_frontend.dto.ProcurementMethodDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.backend_as_frontend.utils.Utils.getToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class ProcurementMethodService {


    private final WebClient webClient;

    private final ProcurementNatureService procurementNatureService;

    private final String baseURI = "http://localhost:9595/v1/procurement_method";


    public ProcurementMethodService(WebClient webClient, ProcurementNatureService procurementNatureService) {
        this.webClient = webClient;
        this.procurementNatureService = procurementNatureService;
    }

    public ProcurementMethod get(Integer id) {

        Mono<ProcurementMethod> entity = webClient.get()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(ProcurementMethod.class);

        return entity.block();

    }


    public List<ProcurementMethod> getAll() {

        Mono<List<ProcurementMethod>> entity = webClient.get()
                .uri(baseURI + "/list")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(ProcurementMethod.class)
                .collectList();

        List<ProcurementMethod> block = entity.block();
        System.out.println("entity.block() = " + block);

        return block;
    }

    public List<ProcurementMethodDTO> getAll(String fieldName) {

        Mono<List<ProcurementMethodDTO>> entity = webClient.get()
                .uri(baseURI + "?fieldName=" + fieldName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(ProcurementMethodDTO.class)
                .collectList();

        List<ProcurementMethodDTO> block = entity.block();
        System.out.println("entity.block() = " + block);
        return block;
    }


    public void save(ProcurementMethod nature) {


        Mono<ProcurementMethod> entity = webClient.post()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(nature), ProcurementMethod.class)
                .retrieve()
                .bodyToMono(ProcurementMethod.class);

        System.out.println("entity.block() = " + entity.block());


    }


    public void update(ProcurementMethod nature) {


        Mono<ProcurementMethod> entity = webClient.put()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(nature), ProcurementMethod.class)
                .retrieve()
                .bodyToMono(ProcurementMethod.class);

        System.out.println("entity.block() = " + entity.block());


    }


    public void delete(Integer id) {

        Mono<ProcurementMethod> entity = webClient.delete()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(ProcurementMethod.class);


        System.out.println("entity.block() = " + entity.block());

    }
}
