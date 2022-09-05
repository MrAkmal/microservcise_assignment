package com.example.procurement_method_service.procurementMethod;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class KeywordBaseDTO {

    private int id;

    private String genericName;

    private String country;

    private String wiseName;

}
