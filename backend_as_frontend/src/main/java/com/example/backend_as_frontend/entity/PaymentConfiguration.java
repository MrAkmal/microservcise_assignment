package com.example.backend_as_frontend.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PaymentConfiguration {


    private int id;


    private int procurementNatureId;

    private int procurementMethodId;


}
