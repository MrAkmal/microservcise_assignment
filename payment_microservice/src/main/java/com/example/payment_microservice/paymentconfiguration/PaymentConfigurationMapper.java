package com.example.payment_microservice.paymentconfiguration;


import com.example.payment_microservice.payment.PaymentBase;
import com.example.payment_microservice.payment.PaymentBaseDTO;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentConfigurationMapper {


    public List<PaymentBaseDTO> toBaseDTO(Flux<PaymentBase> paymentBase) {

        List<PaymentBaseDTO> list = new ArrayList<>();
        paymentBase.subscribe(base -> {
            System.out.println("base = " + base);
            list.add(toBaseDTO(base));
        });
        return list;

    }


    public PaymentBaseDTO toBaseDTO(PaymentBase paymentBase) {


        return PaymentBaseDTO.builder()
                .id(paymentBase.getId())
                .type(paymentBase.getType())
                .active(paymentBase.isActive())
                .build();

    }


//    public List<PaymentConfigurationDTO> toDTO(List<PaymentBase> paymentBase) {
//
//
//        return paymentBase.stream().map(this::toBaseDTO).toList();
//
//    }
//
//
//    public PaymentConfigurationDTO toDTO(PaymentConfiguration paymentConfiguration) {
//
//        return PaymentConfigurationDTO.builder()
//                .id(paymentConfiguration.getId())
//                .procurementNatureName()
//                .procurementMethodName()
//                .payments()
//                .build();
//    }






    public PaymentConfiguration fromCreateDTO(PaymentConfigurationCreateDTO createDTO) {

        return PaymentConfiguration.builder()
                .procurementMethodId(createDTO.getProcurementMethodId())
                .procurementNatureId(createDTO.getProcurementNatureId())
                .build();

    }

    public PaymentConfiguration fromUpdateDTO(PaymentConfigurationUpdateDTO updateDTO) {

        return PaymentConfiguration.builder()
                .id(updateDTO.getId())
                .procurementMethodId(updateDTO.getProcurementMethodId())
                .procurementNatureId(updateDTO.getProcurementNatureId())
                .build();
    }


}
