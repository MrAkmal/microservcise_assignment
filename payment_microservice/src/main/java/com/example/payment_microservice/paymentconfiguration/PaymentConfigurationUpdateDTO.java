package com.example.payment_microservice.paymentconfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfigurationUpdateDTO {


    private int id;

    private int procurementNatureId;

    private int procurementMethodId;

}
