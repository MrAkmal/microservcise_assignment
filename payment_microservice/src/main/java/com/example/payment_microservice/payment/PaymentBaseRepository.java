package com.example.payment_microservice.payment;


import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentBaseRepository extends ReactiveCrudRepository<PaymentBase, Integer> {


    Mono<PaymentBase> findByPaymentConfigurationId(int paymentConfigurationId);


    @Query("\n" +
            "select cast(json_build_object('id', cte.id, 'type', cte.type, 'active', cte.active, 'configuration',cte.configuration) as text)\n" +
            "from (select pb.id,\n" +
            "             pb.is_active                AS active,\n" +
            "             pb.payment_configuration_id AS configuration,\n" +
            "             pt.type                     AS type\n" +
            "      from payment_base pb\n" +
            "               inner join payment_type pt on pb.payment_type_id = pt.id\n" +
            "      where pb.payment_configuration_id = :paymentConfigurationId) cte;")

    Flux<String> findPaymentBasesByPaymentConfigurationId(Integer paymentConfigurationId);


    @Transactional
    @Modifying
    @Query("insert into payment_base(payment_type_id,is_active) values (:#{#dto.paymentTypeId},:#{#dto.active})")
    Mono<PaymentBase> saveCustom(PaymentBase dto);

}
