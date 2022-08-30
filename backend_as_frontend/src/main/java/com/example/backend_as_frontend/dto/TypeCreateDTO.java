package com.example.backend_as_frontend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TypeCreateDTO {


    private int paymentTypeId;

    private boolean active;

    private int paymentConfigurationId;
}
