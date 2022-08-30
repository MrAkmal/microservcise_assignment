package com.example.backend_as_frontend.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentConfigurationDTO {


    private int id;
    private String procurementNatureName;
    private String procurementMethodName;
    private List<PaymentBaseDTO> payments;


}

