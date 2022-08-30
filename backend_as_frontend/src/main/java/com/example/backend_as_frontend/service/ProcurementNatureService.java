package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.entity.ProcurementNature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.backend_as_frontend.utils.Utils.getToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class ProcurementNatureService {


    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595/v1/procurement_nature";


    public ProcurementNatureService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProcurementNature get(Integer id) {

        Mono<ProcurementNature> entity = webClient.get()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(ProcurementNature.class);

        ProcurementNature block = entity.block();
        System.out.println("block = " + block);
        return block;

    }

    public String getProcurementNatureName(Integer id){
        ProcurementNature procurementNature = get(id);
        if (procurementNature!=null) {
            return procurementNature.getName();
        }
        return "null";
    }

    public List<ProcurementNature> getAll() {

        Mono<List<ProcurementNature>> entity = webClient.get()
                .uri(baseURI+"/list")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(ProcurementNature.class)
                .collectList();

        return entity.block();
    }

    public List<ProcurementNature> getAll(String fieldName) {

        Mono<List<ProcurementNature>> entity = webClient.get()
                .uri(baseURI+"?fieldName="+fieldName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(ProcurementNature.class)
                .collectList();

        return entity.block();
    }



    public void save(ProcurementNature nature) {


        Mono<ProcurementNature> entity = webClient.post()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(nature), ProcurementNature.class)
                .retrieve()
                .bodyToMono(ProcurementNature.class);

        System.out.println("entity.block() = " + entity.block());


    }


    public void update(ProcurementNature nature) {


        Mono<ProcurementNature> entity = webClient.put()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(nature), ProcurementNature.class)
                .retrieve()
                .bodyToMono(ProcurementNature.class);

        System.out.println("entity.block() = " + entity.block());


    }


    public void delete(Integer id) {

        Mono<ProcurementNature> entity = webClient.delete()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(ProcurementNature.class);


        System.out.println("entity.block() = " + entity.block());

    }

}
