package com.example.payment_microservice.paymenttype;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentTypeRepository extends ReactiveCrudRepository<PaymentType, Integer> {
}
