package com.example.backend_as_frontend.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class CountryBase {
    private int id;
    private String name;
    private String code;
}
