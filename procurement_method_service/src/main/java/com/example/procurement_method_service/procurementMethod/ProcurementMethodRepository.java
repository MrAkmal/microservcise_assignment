package com.example.procurement_method_service.procurementMethod;


import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface ProcurementMethodRepository extends R2dbcRepository<ProcurementMethod, Integer> {

    @Query("select * from procurement_method where name = :#{#name} ")
    Mono<ProcurementMethod> findProcurementMethodByName(String name);

    @Query("select * from procurement_method where name = :#{#name} and id = :#{#id} ")
    Mono<ProcurementMethod> findProcurementMethodByIdAndName(String name, int id);

    @Query("select * from procurement_method where name = :#{#name} and id <> :#{#id} ")
    Mono<ProcurementMethod> findProcurementMethodByIdAndNameNot(String name, int id);

    @Modifying
    @Transactional
    @Query("delete from procurement_method where procurement_nature_id = :procurementNatureId ")
    Mono<Void> deleteByProcurementNatureId(int procurementNatureId);

    @Modifying
    @Transactional
    @Query("update procurement_method set procurement_nature_id=0 where procurement_nature_id = :procurementNatureId ")
    Mono<Void> updateByProcurementNatureId(int procurementNatureId);


}
