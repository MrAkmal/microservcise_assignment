package com.example.keyword_microservice.keyword;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface KeywordBaseRepository extends ReactiveCrudRepository<KeywordBase, Integer> {

    Mono<KeywordBase> findByCountryId(int name);

    @Query("select * from keyword_base where generic_name= :#{#dto.genericName} and country_id= :#{#dto.countryId} and wise_name= :#{#dto.wiseName}")
    Mono<KeywordBase> checkKeywordNotExist(KeywordBaseCreateDTO dto);

}
