package com.example.procurement_nature_service.procurementNature;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(name = "procurement_nature")
public class ProcurementNature {

    @Id
    @Column("id")
    private int id;

    @Column("name")
    private String name;
}
