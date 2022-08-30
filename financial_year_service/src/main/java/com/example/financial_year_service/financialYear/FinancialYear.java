package com.example.financial_year_service.financialYear;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "financial_year")
public class FinancialYear {


    @Id
    @Column("id")
    private int id;


    @Column("year")
    private String year;


    @Column("is_default")
    private boolean isDefault;

}
