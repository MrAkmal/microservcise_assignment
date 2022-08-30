package com.example.payment_microservice.payment;


import com.example.payment_microservice.paymenttype.PaymentBaseTypeProjection;
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

    @Query("select cast(json_build_object('id',cte.id,'type',cte.paymentType,'active',cte.active,'config',cte.configId) as text)\n" +
            "from (select  payment_base.id,\n" +
            "              pt.type as paymentType,\n" +
            "              payment_base.is_active as active,\n" +
            "              pc.id as configId\n" +
            "      from payment_base\n" +
            "               join payment_type pt on pt.id = payment_base.payment_type_id\n" +
            "               join payment_configuration pc on pc.id = payment_base.payment_configuration_id)cte")
    Flux<String> getAll();

}
