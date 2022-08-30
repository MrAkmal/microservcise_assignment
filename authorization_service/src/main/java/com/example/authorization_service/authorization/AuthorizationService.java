package com.example.authorization_service.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authorization_service.utils.JWTUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthorizationService {


    private final AuthorizationRepository repository;

    public AuthorizationService(AuthorizationRepository repository) {
        this.repository = repository;
    }

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

        repository.save(new Token(accessToken, accessExpiresAt, refreshToken, refreshExpiresAt));

        return new AuthorizationDTO(accessToken, accessExpiresAt, refreshToken, refreshExpiresAt);
    }

    public Object checkToken(HttpServletRequest request) {

        String authHeader = request.getHeader(AUTHORIZATION);


        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            JWTVerifier verifier = JWT.require(JWTUtils.getAlgorithm()).build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return true;

        }

        return false;
    }
}
