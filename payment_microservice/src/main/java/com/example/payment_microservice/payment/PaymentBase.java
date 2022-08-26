package com.example.payment_microservice.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("payment_base")
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
