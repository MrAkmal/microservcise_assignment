package com.example.backend_as_frontend.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Configuration
public class Utils {



    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().build();
    }

    public static String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Cookie[] cookies = request.getCookies();

        String token = "Bearer ";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ACCESSTOKEN")) {
                System.out.println("cookie.getName() = " + cookie.getName());
                System.out.println("cookie.getValue() = " + cookie.getValue());
                token += cookie.getValue();
                break;
            }
        }
        return token;
    }

    public static String getRefreshToken(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Cookie[] cookies = request.getCookies();

        String token = "Bearer ";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("REFRESHTOKEN")) {
                System.out.println("cookie.getName() = " + cookie.getName());
                System.out.println("cookie.getValue() = " + cookie.getValue());
                token += cookie.getValue();
                break;
            }
        }
        return token;
    }
}
