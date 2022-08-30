package com.example.procurement_method_service.procurementMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "procurement_method")
public class ProcurementMethod {

    @Id
    @Column("id")
    private int id;


    @Column("name")
    private String name;


    @Column("procurement_nature_id")
    private int procurementNatureId;

    public ProcurementMethod(String name, int procurementNatureId) {
        this.name = name;
        this.procurementNatureId = procurementNatureId;
    }
}
