package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.TypeCreateDTO;
import com.example.payment_microservice.payment.PaymentBaseCreateDTO;
import lombok.*;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentConfigurationCreateDTO {


    private int id;

    @NotNull
    @Min(1)
    private int procurementNatureId;


    @NotNull
    @Min(1)
    private int procurementMethodId;

    private List<PaymentBaseCreateDTO> types;

}
