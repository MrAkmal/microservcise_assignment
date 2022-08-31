package com.example.payment_microservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TypeCreateDTO {


    private String type;

    private boolean active;

}
