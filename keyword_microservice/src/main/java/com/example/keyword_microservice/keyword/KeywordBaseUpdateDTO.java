package com.example.keyword_microservice.keyword;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class KeywordBaseUpdateDTO {

    @NotNull(message = " required")
    private int id;

    @NotNull(message = " required")
    @Size(min = 2, max = 50)
    private String genericName;

    @NotNull(message = " required")
    private int countryId;

    @NotNull(message = " required")
    @Size(min = 2, max = 50)
    private String wiseName;

}