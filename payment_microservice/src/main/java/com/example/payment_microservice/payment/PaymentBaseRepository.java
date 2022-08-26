package com.example.payment_microservice.payment;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PaymentBaseRepository extends ReactiveCrudRepository<PaymentBase, Integer> {


    Mono<PaymentBase> findByPaymentConfigurationId(int paymentConfigurationId);

    Mono<List<PaymentBase>> findAllByPaymentConfigurationId(int paymentConfigurationId);


}
