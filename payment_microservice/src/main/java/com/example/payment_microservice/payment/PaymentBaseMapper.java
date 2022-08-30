package com.example.payment_microservice.payment;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentBaseMapper {


    public PaymentBase fromCreateDTO(PaymentBaseCreateDTO dto) {

        return PaymentBase.builder()
                .active(dto.isActive())
                .paymentTypeId(dto.getPaymentTypeId())
                .paymentConfigurationId(dto.getPaymentConfigurationId())
                .build();
    }


    public List<PaymentBase> fromCreateDTO(List<PaymentBaseCreateDTO> dtos) {

        return dtos.stream().map(this::fromCreateDTO).toList();
    }



    public PaymentBase fromUpdateDTO(PaymentBaseUpdateDTO dto) {

        return PaymentBase.builder()
                .id(dto.getId())
                .active(dto.isActive())
                .paymentTypeId(dto.getPaymentTypeId())
                .paymentConfigurationId(dto.getPaymentConfigurationId())
                .build();
    }


//    public List<PaymentBaseDTO> toDTO(List<PaymentBase> entities) {
//
//        return entities.stream().map().toList();
//    }


    public PaymentBaseDTO toDTO(PaymentBase entity,String paymentType) {

        return PaymentBaseDTO.builder()
                .id(entity.getId())
                .active(entity.isActive())
                .paymentType(paymentType)
                .paymentConfigurationId(entity.getPaymentConfigurationId())
                .build();
    }


}
