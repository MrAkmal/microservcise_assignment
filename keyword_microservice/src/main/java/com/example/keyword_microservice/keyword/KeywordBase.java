package com.example.keyword_microservice.keyword;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table("keyword_base")
@Builder
public class    KeywordBase {

    @Id
    @Column("id")
    private int id;

    @Column("generic_name")
    //Generic
    private String genericName;

    @Column("country_id")
    private int countryId;

    @Column("wise_name")
    private String wiseName;

    public KeywordBase(String genericName, int countryId, String wiseName) {
        this.genericName = genericName;
        this.countryId = countryId;
        this.wiseName = wiseName;
    }
}
