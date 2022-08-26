package com.example.payment_microservice.payment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payment_base")
public class PaymentBaseController {


    private final PaymentBaseService service;

    @Autowired
    public PaymentBaseController(PaymentBaseService service) {
        this.service = service;
    }





}
