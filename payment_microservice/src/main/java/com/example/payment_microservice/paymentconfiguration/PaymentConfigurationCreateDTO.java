package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.TypeCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfigurationCreateDTO {



    private int procurementNatureId;

    private int procurementMethodId;

    private List<TypeCreateDTO> types;

}
