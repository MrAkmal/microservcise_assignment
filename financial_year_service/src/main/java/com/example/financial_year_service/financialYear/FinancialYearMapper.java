package com.example.financial_year_service.financialYear;


import com.example.financial_year_service.financialYear.dto.FinancialYearCreateDTO;
import com.example.financial_year_service.financialYear.dto.FinancialYearDTO;
import com.example.financial_year_service.financialYear.dto.FinancialYearUpdateDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialYearMapper {

    public FinancialYear fromCreateDTO(FinancialYearCreateDTO dto) {


        return FinancialYear.builder()
                .year(dto.getYear())
                .isDefault(dto.isDefault())
                .build();

    }

    public FinancialYear fromUpdateDTO(FinancialYearUpdateDTO dto) {

        return FinancialYear.builder()
                .id(dto.getId())
                .year(dto.getYear())
                .isDefault(dto.isDefault())
                .build();
    }

    public FinancialYearDTO toDTO(FinancialYear financialYear) {


        return FinancialYearDTO.builder()
                .id(financialYear.getId())
                .year(financialYear.getYear())
                .isDefault(financialYear.isDefault())
                .build();
    }


    public List<FinancialYearDTO> toDTO(List<FinancialYear> financialYears) {
        return financialYears.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
