package com.example.keyword_microservice.country;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "country_base")
public class CountryBase {


    @Id
    @Column("id")
    private int id;

    @Column("name")
    private String name;

    @Column("code")
    private String code;

}
