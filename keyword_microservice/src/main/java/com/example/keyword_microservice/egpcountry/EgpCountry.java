package com.example.keyword_microservice.egpcountry;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "egp_country")
public class EgpCountry {


    @Id
    @Column("id")
    private int id;

    @Column("is_default")
    private boolean isDefault;

    @Column("country_id")
    private int countryId;

    public EgpCountry(boolean isDefault, int countryId) {
        this.isDefault = isDefault;
        this.countryId = countryId;
    }
}
