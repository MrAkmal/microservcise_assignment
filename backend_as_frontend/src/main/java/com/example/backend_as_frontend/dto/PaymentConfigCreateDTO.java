package com.example.backend_as_frontend.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PaymentConfigCreateDTO {

    private int id;

    private int procurementNatureId;

    private int procurementMethodId;

    private List<TypeCreateDTO> types;
}
