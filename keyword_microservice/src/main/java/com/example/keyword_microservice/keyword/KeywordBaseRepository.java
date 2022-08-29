package com.example.keyword_microservice.keyword;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface KeywordBaseRepository extends ReactiveCrudRepository<KeywordBase, Integer> {

    Mono<KeywordBase> findByCountry(String name);

}
