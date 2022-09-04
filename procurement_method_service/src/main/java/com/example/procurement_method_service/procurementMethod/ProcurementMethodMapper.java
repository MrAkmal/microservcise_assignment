package com.example.procurement_method_service.procurementMethod;

import org.springframework.stereotype.Component;

@Component
public class ProcurementMethodMapper {

    public ProcurementMethodDTO toDTO(int id, String wiseName, String procurementName) {
        return ProcurementMethodDTO.builder()
                .id(id)
                .wiseName(wiseName)
                .procurementNature(procurementName)
                .build();
    }

    public ProcurementMethodUpdateDTO toUpdateDTO(ProcurementMethod procurementMethod) {
        return ProcurementMethodUpdateDTO.builder()
                .id(procurementMethod.getId())
                .keywordBaseId(procurementMethod.getKeywordBaseId())
                .procurementNatureId(procurementMethod.getProcurementNatureId())
                .build();
    }

    public ProcurementMethod fromCreateDTO(ProcurementMethodCreateDTO dto) {
        return ProcurementMethod.builder()
                .procurementNatureId(dto.getProcurementNatureId())
                .keywordBaseId(dto.getKeywordBaseId())
                .build();
    }

    public ProcurementMethod fromUpdateDTO(ProcurementMethodUpdateDTO dto) {
        return ProcurementMethod.builder()
                .id(dto.getId())
                .procurementNatureId(dto.getProcurementNatureId())
                .keywordBaseId(dto.getKeywordBaseId())
                .build();

    }
}
