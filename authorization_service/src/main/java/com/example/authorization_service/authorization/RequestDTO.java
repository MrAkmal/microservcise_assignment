package com.example.authorization_service.authorization;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {


    private String username;

    private List<String> authorities;

    private String issuer;


}
