package com.example.backend_as_frontend.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EgpCountryUpdateDTO {

    @NotNull(message = " required")
    private int id;

    @NotNull(message = " required")
    private int countryId;

    @NotNull(message = " required")
    private boolean isDefault;

}
