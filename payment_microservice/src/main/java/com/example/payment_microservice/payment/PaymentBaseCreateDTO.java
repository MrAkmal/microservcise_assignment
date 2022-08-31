package com.example.payment_microservice.payment;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentBaseCreateDTO {



    @NotNull
    @Min(1)
    private int paymentTypeId;

    @NotNull
    private boolean active;

    @NotNull
    @Min(1)
    private int paymentConfigurationId;


}
