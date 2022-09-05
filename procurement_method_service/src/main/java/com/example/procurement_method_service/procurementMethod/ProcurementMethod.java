package com.example.procurement_method_service.procurementMethod;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "procurement_method")
@Builder
public class ProcurementMethod {

    @Id
    @Column("id")
    private int id;


    @Column("keyword_base_id")
    private int keywordBaseId;


    @Column("procurement_nature_id")
    private int procurementNatureId;

}
