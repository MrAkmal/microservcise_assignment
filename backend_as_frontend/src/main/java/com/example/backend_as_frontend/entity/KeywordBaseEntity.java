package com.example.backend_as_frontend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KeywordBaseEntity {

    private int id;

    private String genericName;

    private int countryId;

    private String wiseName;

}
