package com.example.payment_microservice.payment;


import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentBaseRepository extends ReactiveCrudRepository<PaymentBase, Integer> {


    Mono<PaymentBase> findByPaymentConfigurationId(int paymentConfigurationId);


    @Query("select * from payment_base where payment_configuration_id =:paymentConfigurationId")
    Flux<PaymentBase> findPaymentBasesByPaymentConfigurationId(Integer paymentConfigurationId);


    @Transactional
    @Modifying
    @Query("insert into payment_base(type,is_active) values (:#{#dto.type},:#{#dto.active})")
    Mono<PaymentBase> saveCustom(PaymentBase dto);

}
