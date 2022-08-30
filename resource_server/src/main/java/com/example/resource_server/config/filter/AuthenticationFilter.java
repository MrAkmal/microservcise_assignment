package com.example.resource_server.config.filter;

import com.auth0.jwt.JWT;
import com.example.resource_server.dto.*;
import com.example.resource_server.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;


    public AuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper mapper) {
        super.setFilterProcessesUrl("/login");
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginDto loginDto = mapper.readValue(request.getReader(), LoginDto.class);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return authenticationManager.authenticate(token);


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<RequestDTO> entity = new HttpEntity<>(new RequestDTO(user.getUsername(), authorities, request.getRequestURI()), headers);

        SessionDTO sessionDTO = restTemplate.exchange("http://localhost:7070/v1/authorization_server/generate-token",
                HttpMethod.POST, entity, SessionDTO.class).getBody();


        DataDTO data = new DataDTO(sessionDTO);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), data);

    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        DataDTO dto = new DataDTO(new ErrorDTO(LocalDateTime.now(), failed.getMessage()));
        mapper.writeValue(response.getOutputStream(), dto);

    }
}
