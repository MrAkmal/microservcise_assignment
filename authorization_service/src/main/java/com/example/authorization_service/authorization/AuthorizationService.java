package com.example.authorization_service.authorization;

import com.auth0.jwt.JWT;
import com.example.authorization_service.utils.JWTUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthorizationService {


    public AuthorizationDTO generateToken(RequestDTO dto) {

        String accessToken = JWT.create()
                .withSubject(dto.getUsername())
                .withExpiresAt(JWTUtils.getExpiresAt())
                .withClaim("roles", dto.getAuthorities())
                .withIssuer(dto.getIssuer())
                .sign(JWTUtils.getAlgorithm());


        String refreshToken = JWT.create()
                .withSubject(dto.getUsername())
                .withExpiresAt(Date.from(JWTUtils.getExpiresAt().toInstant().plus(10, ChronoUnit.MINUTES)))
                .withIssuer(dto.getIssuer())
                .sign(JWTUtils.getAlgorithm());


        LocalDateTime accessExpiresAt = LocalDateTime.ofInstant(JWTUtils.getExpiresAt().toInstant(), ZoneId.systemDefault());
        LocalDateTime refreshExpiresAt = LocalDateTime.ofInstant(JWTUtils.getExpiresAt().toInstant().plus(10, ChronoUnit.MINUTES), ZoneId.systemDefault());

        return new AuthorizationDTO(accessToken, accessExpiresAt, refreshToken, refreshExpiresAt);
    }

}
