package com.example.payment_microservice.paymenttype;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PaymentTypeRepository extends R2dbcRepository<PaymentType, Integer> {
}
