package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.dto.KeywordBaseCreateDTO;
import com.example.backend_as_frontend.dto.KeywordBaseUpdateDTO;
import com.example.backend_as_frontend.entity.KeywordBase;
import com.example.backend_as_frontend.entity.KeywordBaseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.backend_as_frontend.utils.Utils.getToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class KeywordBaseService {


    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595/v1/key_word_base/keyword_base_server";


    public KeywordBaseService(WebClient webClient) {
        this.webClient = webClient;
    }

    public KeywordBase get(Integer id) {

        Mono<KeywordBase> entity = webClient.get()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(KeywordBase.class);

        KeywordBase block = entity.block();
        System.out.println("block = " + block);
        return block;

    }

    public KeywordBaseUpdateDTO getKeywordBase(Integer id) {

        Mono<KeywordBaseEntity> entity = webClient.get()
                .uri(baseURI + "/keywordBase/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(KeywordBaseEntity.class);

        KeywordBaseEntity block = entity.block();
        System.out.println("block = " + block);
        return new KeywordBaseUpdateDTO(block.getId(), block.getGenericName(), block.getCountryId(), block.getWiseName());

    }

    public List<KeywordBase> getAll() {

        Mono<List<KeywordBase>> entity = webClient.get()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(KeywordBase.class)
                .collectList();

        return entity.block();
    }

    public List<KeywordBase> getAll(String fieldName) {

        Mono<List<KeywordBase>> entity = webClient.get()
                .uri(baseURI + "?fieldName=" + fieldName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToFlux(KeywordBase.class)
                .collectList();

        return entity.block();
    }


    public void save(KeywordBaseCreateDTO nature) {


        Mono<KeywordBase> entity = webClient.post()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(nature), KeywordBase.class)
                .retrieve()
                .bodyToMono(KeywordBase.class);

        System.out.println("entity.block() = " + entity.block());
    }


    public void update(KeywordBaseUpdateDTO nature) {

        Mono<KeywordBase> entity = webClient.put()
                .uri(baseURI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .body(Mono.just(nature), KeywordBase.class)
                .retrieve()
                .bodyToMono(KeywordBase.class);

        System.out.println("entity.block() = " + entity.block());


    }


    public void delete(Integer id) {

        Mono<KeywordBase> entity = webClient.delete()
                .uri(baseURI + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(KeywordBase.class);


        System.out.println("entity.block() = " + entity.block());

    }

    public String getWiseName() {

        Mono<String> wiseName = webClient.get()
                .uri(baseURI + "/wise_name")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getToken())
                .retrieve()
                .bodyToMono(String.class);

        return wiseName.block();
    }
}
