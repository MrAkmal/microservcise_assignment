package com.example.api_gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class AuthenticationFilter implements GatewayFilter {


    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                throw new RuntimeException("Header not valid");

            String header = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
            String token = header.substring("Bearer ".length());

            System.out.println("header = " + header);
            System.out.println("token = " + token);

            if (isExpired(token)) throw new RuntimeException("Token expired");

            JWTVerifier jwtVerifier = JWT.require(jwtUtil.getAlgorithm()).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            String subject = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

            exchange.getRequest().mutate()
                    .header("username", subject)
                    .header("roles", roles)
                    .build();

        }

        return chain.filter(exchange);


    }

    private boolean isExpired(String token) {

        JWTVerifier jwtVerifier = JWT.require(jwtUtil.getAlgorithm()).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT.getExpiresAt().before(new Date());
    }


}
