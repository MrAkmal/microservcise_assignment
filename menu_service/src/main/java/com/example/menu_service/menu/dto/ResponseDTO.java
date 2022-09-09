package com.example.menu_service.menu.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<D, E> {


    private int statusCode;

    private D dataDTO;

    private E errorDTO;


}
