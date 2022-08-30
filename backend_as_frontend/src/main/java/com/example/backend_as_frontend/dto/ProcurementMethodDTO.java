package com.example.backend_as_frontend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProcurementMethodDTO {
    private int id;

    private String name;

    private String procurementNatureName;
}
