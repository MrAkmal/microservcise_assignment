package com.example.payment_microservice.payment;

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
@Table(name = "payment_base")
public class PaymentBase {

    @Id
    @Column("id")
    private int id;

    @Column("type")
    private String type;


    @Column("is_active")
    private boolean active;


    @Column("payment_configuration_id")
    private int paymentConfigurationId;

}
