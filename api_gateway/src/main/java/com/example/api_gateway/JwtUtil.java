package com.example.api_gateway;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {


    private final String secretKey = "jbvnafdvbn@#fgd12454_41841dfas%;?fafoanvfgwaifhidhs%&cpoasc";


    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
