package com.example.keyword_microservice.keyword;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/key_word_base/keyword_base_server")
public class KeywordBaseController {

    private final KeywordBaseService service;

    @Autowired
    public KeywordBaseController(KeywordBaseService service) {
        this.service = service;
    }


    @PostMapping
    public Mono<KeywordBase> save(@Valid @RequestBody KeywordBaseCreateDTO dto) {

        return service.save(dto);
    }

    @PutMapping
    public Mono<KeywordBaseDTO> update(@Valid @RequestBody KeywordBaseUpdateDTO dto) {
        return service.update(dto);
    }

    @DeleteMapping("/{keywordBaseId}")
    public Mono<Void> delete(@PathVariable Integer keywordBaseId) {
        return service.delete(keywordBaseId);
    }

    @GetMapping("/{keywordBaseId}")
    public Mono<KeywordBaseDTO> get(@PathVariable Integer keywordBaseId) {
        return service.get(keywordBaseId);
    }

    @GetMapping("/keywordBase/{keywordBaseId}")
    public Mono<KeywordBase> getKeywordBase(@PathVariable Integer keywordBaseId) {
        return service.getKeywordBase(keywordBaseId);
    }

    @GetMapping
    public Flux<KeywordBaseDTO> getAll() {
        return service.getAll();
    }


    @GetMapping("/wise_name")
    public Flux<KeywordBase> getWiseName(){
        return service.getWiseName();
    }

    @GetMapping("/country/{keywordBaseId}/{defaultCountryId}")
    public Mono<KeywordBaseDTO> getKeywordBaseByDefaultCountry(@PathVariable Integer keywordBaseId,@PathVariable Integer defaultCountryId){
        return service.getKeywordBaseByDefaultCountry(keywordBaseId,defaultCountryId);
    }

}
