package com.example.payment_microservice.payment;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentBaseMapper {


    public PaymentBase fromCreateDTO(PaymentBaseCreateDTO dto) {

        return PaymentBase.builder()
                .active(dto.isActive())
                .type(dto.getType())
                .paymentConfigurationId(dto.getPaymentConfigurationId())
                .build();
    }


    public PaymentBase fromUpdateDTO(PaymentBaseUpdateDTO dto) {

        return PaymentBase.builder()
                .id(dto.getId())
                .active(dto.isActive())
                .type(dto.getType())
                .paymentConfigurationId(dto.getPaymentConfigurationId())
                .build();
    }


    public List<PaymentBaseDTO> toDTO(List<PaymentBase> entities) {

        return entities.stream().map(this::toDTO).toList();
    }


    public PaymentBaseDTO toDTO(PaymentBase entity) {

        return PaymentBaseDTO.builder()
                .id(entity.getId())
                .active(entity.isActive())
                .type(entity.getType())
                .paymentConfigurationId(entity.getPaymentConfigurationId())
                .build();
    }


}
