package com.example.payment_microservice.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentBaseUpdateDTO {


    private int id;

    private String type;


    private boolean active;


    private int paymentConfigurationId;


}
