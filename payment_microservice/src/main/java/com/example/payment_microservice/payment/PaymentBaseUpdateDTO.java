package com.example.payment_microservice.payment;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentBaseUpdateDTO {


    @NotNull
    @Min(1)
    private int id;


    @NotNull
    @Min(1)
    private int paymentTypeId;



    @NotBlank
    private boolean active;


    @NotNull
    @Min(1)
    private int paymentConfigurationId;


}
