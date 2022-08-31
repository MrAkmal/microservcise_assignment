package com.example.backend_as_frontend.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeywordBase {

    private int id;

    private String generatedName;

    private String country;

    private String countryWiseName;

}
