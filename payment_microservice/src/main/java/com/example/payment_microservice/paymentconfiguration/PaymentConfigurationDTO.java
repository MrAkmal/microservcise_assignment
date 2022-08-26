package com.example.payment_microservice.paymentconfiguration;


import com.example.payment_microservice.payment.PaymentBaseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentConfigurationDTO {


    private int id;
    private String procurementNatureName;
    private String procurementMethodName;
//    private Map<String, Boolean> payments;
    private List<PaymentBaseDTO> payments;


}
