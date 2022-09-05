package com.example.keyword_microservice.keyword;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

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
