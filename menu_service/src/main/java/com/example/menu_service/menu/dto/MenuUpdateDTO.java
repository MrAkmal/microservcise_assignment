package com.example.menu_service.menu.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuUpdateDTO {


    private int id;

    private int parentId;

    private int keywordId;

    private int roleId;


}
