package com.example.backend_as_frontend.dto;


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
