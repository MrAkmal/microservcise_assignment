package com.example.procurement_method_service.procurementMethod;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ProcurementMethodCreateDTO {
    private int keywordBaseId;
    private int procurementNatureId;
}
