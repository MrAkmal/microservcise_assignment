package com.example.payment_microservice.paymenttype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
@Getter
@ToString
public class PaymentBaseTypeProjection {
    private String id;

    private String type;

    private String active;


    private String config;
}
