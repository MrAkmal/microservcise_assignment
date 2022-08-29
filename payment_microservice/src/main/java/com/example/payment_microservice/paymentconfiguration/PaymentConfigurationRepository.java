package com.example.payment_microservice.paymentconfiguration;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentConfigurationRepository extends ReactiveCrudRepository<PaymentConfiguration, Integer> {


}
