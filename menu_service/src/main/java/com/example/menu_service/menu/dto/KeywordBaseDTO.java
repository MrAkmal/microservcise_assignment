package com.example.menu_service.menu.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class KeywordBaseDTO {

    private int id;

    private String genericName;

    private String country;

    private String wiseName;

}
