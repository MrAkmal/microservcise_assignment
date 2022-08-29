package com.example.keyword_microservice.keyword;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KeywordBaseDTO {

    private int id;

    private String generatedName;

    @NotNull(message = " country is required")
    @Length(min = 2, max = 60)
    private String country;

    @NotNull(message = " countryWiseName is required")
    @Length(min = 2, max = 10)
    private String countryWiseName;

}
