package com.example.procurement_method_service.procurementMethod;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ProcurementMethodUpdateDTO {
    private int id;
    private int keywordBaseId;
    private int procurementNatureId;
}
