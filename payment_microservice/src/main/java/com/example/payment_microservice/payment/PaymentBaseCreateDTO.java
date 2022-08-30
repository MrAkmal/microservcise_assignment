package com.example.payment_microservice.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentBaseCreateDTO {



    private String type;

    private boolean active;

    private int paymentConfigurationId;


}
