package com.example.payment_microservice.paymentconfiguration;


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
@Table("payment_configuration")
public class PaymentConfiguration {


    @Id
    @Column("id")
    private int id;


    @Column("procurement_nature_id")
    private int procurementNatureId;

    @Column("procurement_method_id")
    private int procurementMethodId;


}
