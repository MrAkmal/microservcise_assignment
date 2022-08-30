package com.example.authorization_service.authorization;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AuthorizationDTO {

    private String accessToken;

    private LocalDateTime expiresAt;

    private String refreshToken;

    private LocalDateTime refreshExpiresAt;


}
