package com.example.payment_microservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentBaseService {


    private final PaymentBaseRepository repository;

    @Autowired
    public PaymentBaseService(PaymentBaseRepository repository) {
        this.repository = repository;
    }
}
