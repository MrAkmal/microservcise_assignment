package com.example.payment_microservice.paymentconfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfigurationUpdateDTO {


    @NotNull
    @Min(1)
    private int id;


    @NotNull
    @Min(1)
    private int procurementNatureId;


    @NotNull
    @Min(1)
    private int procurementMethodId;

}
