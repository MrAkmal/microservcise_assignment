package com.example.payment_microservice.paymentconfiguration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/payment_configuration")
public class PaymentConfigurationController {

    private final PaymentConfigurationService service;


    @Autowired
    public PaymentConfigurationController(PaymentConfigurationService service) {
        this.service = service;
    }


    @GetMapping
    public Flux<PaymentConfigurationDTO> getAll() {

        return  service.getAll();
    }


}
