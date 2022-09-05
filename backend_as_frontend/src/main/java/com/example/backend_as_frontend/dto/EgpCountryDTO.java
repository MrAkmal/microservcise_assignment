package com.example.backend_as_frontend.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class EgpCountryDTO {

    private int id;
    private boolean isDefault;
    private String countryName;

}
