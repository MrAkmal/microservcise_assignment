package com.example.keyword_microservice.egpcountry;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EgpCountryCreateDTO {

    @NotNull(message = " required")
    private int countryId;

    @NotNull(message = " required")
    private boolean isDefault;
}
