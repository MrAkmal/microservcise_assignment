package com.example.backend_as_frontend.service;

import com.example.backend_as_frontend.dto.DataDTO;
import com.example.backend_as_frontend.dto.LoginDTO;
import com.example.backend_as_frontend.dto.ResetPasswordDTO;
import com.example.backend_as_frontend.dto.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.example.backend_as_frontend.utils.Utils.getRefreshToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthService {


    private final WebClient webClient;

    private final String baseURI = "http://localhost:9595";

    @Autowired
    public AuthService(WebClient webClient) {
        this.webClient = webClient;
    }


    public SessionDTO login(LoginDTO dto) {

        System.out.println("dto.getUsername() = " + dto.getUsername());
        System.out.println("dto.getPassword() = " + dto.getPassword());

        Mono<DataDTO> mono = webClient.post()
                .uri(baseURI + "/login")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(dto), LoginDTO.class)
                .retrieve()
                .bodyToMono(DataDTO.class);

        DataDTO block = mono.block();
        System.out.println("block.getAccessToken() = " + block.getBody().getAccessToken());
        System.out.println("block.getExpiresAt() = " + block.getBody());

        return block.getBody();
    }


    public String sendCode(String username) {


        Mono<String> mono = webClient.post()
                .uri(baseURI + "/send-code")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(username), String.class)
                .retrieve()
                .bodyToMono(String.class);

        String block = mono.block();
        System.out.println("mono.block() = " + block);

        return block;
    }

    public SessionDTO refreshToken() {

        Mono<DataDTO> mono = webClient.post()
                .uri(baseURI + "/login")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, getRefreshToken())
                .retrieve()
                .bodyToMono(DataDTO.class);

        DataDTO block = mono.block();
        System.out.println("block.getAccessToken() = " + block.getBody().getAccessToken());
        System.out.println("block.getExpiresAt() = " + block.getBody());

        return block.getBody();
    }


    public void resetPassword(ResetPasswordDTO dto, String token) {


        Mono<Boolean> mono = webClient.post()
                .uri(baseURI + "/reset-password?token=" + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(dto), ResetPasswordDTO.class)
                .retrieve()
                .bodyToMono(Boolean.class);


        Boolean block = mono.block();

        System.out.println("block = " + block);

    }


    public void logout(HttpServletResponse response) {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Cookie accessToken = new Cookie("ACCESSTOKEN", null);
        accessToken.setMaxAge(0);
        accessToken.setSecure(true);
        accessToken.setHttpOnly(true);

        Cookie refreshToken = new Cookie("REFRESHTOKEN", null);
        refreshToken.setMaxAge(0);
        refreshToken.setSecure(true);
        refreshToken.setHttpOnly(true);

        response.addCookie(refreshToken);
        response.addCookie(accessToken);
    }
}
