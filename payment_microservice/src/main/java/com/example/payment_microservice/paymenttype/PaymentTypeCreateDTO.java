package com.example.payment_microservice.paymenttype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeCreateDTO {

    @NotBlank
    private String type;

}
