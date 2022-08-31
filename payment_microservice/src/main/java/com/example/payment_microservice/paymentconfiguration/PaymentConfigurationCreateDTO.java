package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.TypeCreateDTO;
import com.example.payment_microservice.payment.PaymentBaseCreateDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentConfigurationCreateDTO {


    private int id;

    private int procurementNatureId;

    private int procurementMethodId;

    private List<PaymentBaseCreateDTO> types;

}
