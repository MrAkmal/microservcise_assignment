package com.example.payment_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcurementMethodDTO {

    private int id;


    private String name;


    private int procurementNatureId;
}