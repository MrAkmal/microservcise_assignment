package com.example.payment_microservice.paymentconfiguration;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "payment_configuration")
public class PaymentConfiguration {


    @Id
    @Column("id")
    private int id;


    @Column("procurement_nature_id")
    private int procurementNatureId;

    @Column("procurement_method_id")
    private int procurementMethodId;


}
