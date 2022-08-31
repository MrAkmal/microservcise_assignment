package com.example.payment_microservice.paymentconfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfigurationCreateDTO {



    @NotNull
    @Min(1)
    private int procurementNatureId;


    @NotNull
    @Min(1)
    private int procurementMethodId;


}
