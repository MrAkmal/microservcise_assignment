package com.example.menu_service.utils;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
@Component
public class JWTUtils {


    public static String secretKey = "jbvnafdvbn@#fgd12454_41841dfas%;?fafoanvfgwaifhidhs%&cpoasc";

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
