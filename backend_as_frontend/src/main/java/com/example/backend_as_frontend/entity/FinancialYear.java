package com.example.backend_as_frontend.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FinancialYear {


    private int id;

    private String year;

    private boolean isDefault;

}
