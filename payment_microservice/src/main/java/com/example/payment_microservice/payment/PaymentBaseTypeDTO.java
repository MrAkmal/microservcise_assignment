package com.example.payment_microservice.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PaymentBaseTypeDTO {


    private int id;

    private String type;

    private boolean active;


    private int configuration;

}
