package com.example.procurement_nature_service.procurementNature;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProcurementNatureDTO {

    private int id;


    @NotNull(message = " name is required")
    @Length(min = 2, max = 60)
    private String name;
}
