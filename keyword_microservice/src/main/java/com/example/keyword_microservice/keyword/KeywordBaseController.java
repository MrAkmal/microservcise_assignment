package com.example.keyword_microservice.keyword;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/keyword_base_server")
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

    @GetMapping
    public Flux<KeywordBaseDTO> getAll() {
        return service.getAll();
    }
}
