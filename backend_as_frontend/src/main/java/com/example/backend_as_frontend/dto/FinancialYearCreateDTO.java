package com.example.backend_as_frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialYearCreateDTO {



    private int id;

    private Integer yearFrom;

    private Integer yearTo;

    private boolean isDefault;

}
