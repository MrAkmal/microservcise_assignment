package com.example.keyword_microservice.egpcountry;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EgpCountryRepository extends ReactiveCrudRepository<EgpCountry, Integer> {

    @Query("select * from egp_country where is_default = :check")
    Mono<EgpCountry> findByDefault(boolean check);

    @Query("select * from egp_country where country_id = :countryId ")
    Mono<EgpCountry> findByCountryId(int countryId);

    @Query("select * from egp_country where is_default = :check")
    Flux<EgpCountry> findByDefaultCountry(boolean check);


}
