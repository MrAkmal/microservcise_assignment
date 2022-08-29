package com.example.payment_microservice.payment;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PaymentBaseDTO {


    private int id;

    private String type;

    private boolean active;



}
