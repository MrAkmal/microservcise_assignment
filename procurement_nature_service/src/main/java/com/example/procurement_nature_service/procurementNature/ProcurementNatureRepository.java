package com.example.procurement_nature_service.procurementNature;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProcurementNatureRepository extends R2dbcRepository<ProcurementNature, Integer> {
    @Query("select * from procurement_nature where name = :#{#name} ")
    Mono<ProcurementNature> findProcurementNatureByName(String name);


    @Query("select * from procurement_nature where name = :#{#name} and id = :#{#id} ")
    Mono<ProcurementNature> findProcurementNatureByIdAndName(String name, int id);

    @Query("select * from procurement_nature where name = :#{#name} and id <> :#{#id} ")
    Mono<ProcurementNature> findProcurementNatureIdAndNameNot(String name, int id);
}
