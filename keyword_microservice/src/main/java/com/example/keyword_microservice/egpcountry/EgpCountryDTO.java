package com.example.keyword_microservice.egpcountry;


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
