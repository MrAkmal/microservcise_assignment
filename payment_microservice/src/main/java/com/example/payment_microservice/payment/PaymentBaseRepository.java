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


    @Query("\n" +
            "select cast(json_build_object('id', cte.id, 'type', cte.type, 'active', cte.active, 'configuration',cte.configuration) as text)\n" +
            "from (select pb.id,\n" +
            "             pb.is_active                AS active,\n" +
            "             pb.payment_configuration_id AS configuration,\n" +
            "             pt.type                     AS type\n" +
            "      from payment_base pb\n" +
            "               inner join payment_type pt on pb.payment_type_id = pt.id\n" +
            "      where pb.payment_configuration_id = :paymentConfigurationId order by pb.payment_type_id) cte;")
    Flux<String> findPaymentBasesByPaymentConfigurationId(Integer paymentConfigurationId);


    @Transactional
    @Modifying
    @Query("insert into payment_base(payment_type_id,is_active) values (:#{#dto.paymentTypeId},:#{#dto.active})")
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

    @Modifying
    @Transactional
    @Query("delete from payment_base where payment_configuration_id = :id ")
    Mono<Void> deleteByPaymentConfigId(Integer id);
}
