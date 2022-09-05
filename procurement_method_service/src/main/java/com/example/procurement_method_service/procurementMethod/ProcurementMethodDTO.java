package com.example.procurement_method_service.procurementMethod;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ProcurementMethodDTO {

    private int id;

    private String wiseName;

    private String procurementNature;

}
