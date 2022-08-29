package com.example.keyword_microservice.keyword;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table("keyword_base")
public class KeywordBase {

    @Id
    @Column("id")
    private int id;

    @Column("generated_name")
    private String generatedName;

    @Column("country")
    private String country;

    @Column("country_wise_name")
    private String countryWiseName;

    public KeywordBase(String generatedName, String country, String countryWiseName) {
        this.generatedName = generatedName;
        this.country = country;
        this.countryWiseName = countryWiseName;
    }
}
