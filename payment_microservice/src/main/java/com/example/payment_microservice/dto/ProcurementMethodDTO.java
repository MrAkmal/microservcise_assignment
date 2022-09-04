package com.example.payment_microservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcurementMethodDTO {

    private int id;

    private String wiseName;

    private String procurementNature;
}