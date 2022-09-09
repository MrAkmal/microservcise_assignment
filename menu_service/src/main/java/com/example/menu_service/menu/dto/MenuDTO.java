package com.example.menu_service.menu.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDTO {

    private int id;

    private int parentId;

    private String wiseName;

    private String genericName;



}
