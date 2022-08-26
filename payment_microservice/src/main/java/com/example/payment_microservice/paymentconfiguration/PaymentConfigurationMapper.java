package com.example.payment_microservice.paymentconfiguration;


import com.example.payment_microservice.payment.PaymentBaseDTO;
import com.example.payment_microservice.payment.PaymentBaseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PaymentConfigurationMapper {


    private final PaymentBaseRepository repository;

    public PaymentConfigurationMapper(PaymentBaseRepository repository) {
        this.repository = repository;
    }

    public Mono<PaymentConfigurationDTO> toDto(PaymentConfiguration entity) {

        Mono<PaymentConfigurationDTO> map = repository.findByPaymentConfigurationId(entity.getId()).map(paymentBase ->
                PaymentConfigurationDTO.builder()
                        .id(entity.getId())
                        .procurementNatureName("1")
                        .procurementMethodName("2")
                        .payments(List.of(new PaymentBaseDTO(paymentBase.getId(), paymentBase.getType(), paymentBase.isActive())))
                        .build());

        return map;

    }


}
