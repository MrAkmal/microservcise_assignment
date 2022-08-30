package com.example.payment_microservice.paymenttype;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment_type")
public class PaymentType {


    @Id
    @Column("id")
    private int id;


    @Column("type")
    private String type;


    public PaymentType(String type) {
        this.type = type;
    }

}
