package com.example.procurement_method_service.procurementMethod;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProcurementMethodDTO {

    private int id;

    @NotNull(message = " name is required")
    @Length(min = 2, max = 60)
    private String name;

    @NotNull(message = " procurementNature is required")
    private int procurementNatureId;

}
