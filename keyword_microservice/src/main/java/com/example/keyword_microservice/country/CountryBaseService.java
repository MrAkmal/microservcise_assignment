package com.example.keyword_microservice.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CountryBaseService {

    private final CountryBaseRepository repository;


    @Autowired
    public CountryBaseService(CountryBaseRepository repository) {
        this.repository = repository;
    }

    public Flux<CountryBase> getAll() {
        return repository.findAll();
    }

    public Mono<CountryBase> get(Integer id) {
        return repository.findById(id).switchIfEmpty(Mono.empty());
    }

}
