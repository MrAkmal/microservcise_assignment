package com.example.payment_microservice.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentBaseCreateDTO {



    private int paymentTypeId;

    private boolean active;

    private int paymentConfigurationId;


}
