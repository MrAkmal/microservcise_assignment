package com.example.procurement_method_service.procurementMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProcurementMethodResponse {
    private int id;

    private String name;

    private String procurementNatureName;
}
