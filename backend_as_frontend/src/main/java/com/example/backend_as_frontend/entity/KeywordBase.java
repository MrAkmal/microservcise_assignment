package com.example.backend_as_frontend.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeywordBase {

    private int id;

    private String genericName;

    private String country;

    private String wiseName;

}
