package com.example.keyword_microservice.country;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CountryBaseRepository extends ReactiveCrudRepository<CountryBase, Integer> {


}
