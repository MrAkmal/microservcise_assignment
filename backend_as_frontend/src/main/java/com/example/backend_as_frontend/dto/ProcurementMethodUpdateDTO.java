
package com.example.backend_as_frontend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProcurementMethodUpdateDTO {
    private int id;
    private int keywordBaseId;
    private int procurementNatureId;
}
