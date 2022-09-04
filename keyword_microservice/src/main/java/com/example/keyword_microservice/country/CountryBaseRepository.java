package com.example.keyword_microservice.country;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CountryBaseRepository extends ReactiveCrudRepository<CountryBase, Integer> {


    Mono<CountryBase> findByName(String name);
}
