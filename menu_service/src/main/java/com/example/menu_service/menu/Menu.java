package com.example.menu_service.menu;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("menu_base")
public class Menu {


    @Id
    @Column("id")
    private int id;


    @Column("parent_id")
    private int parentId;


    @Column("keyword_id")
    private int keywordId;


    @Column("role_id")
    private int roleId;


}
