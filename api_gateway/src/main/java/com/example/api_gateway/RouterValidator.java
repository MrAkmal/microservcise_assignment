package com.example.api_gateway;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {


    public static final List<String> WHITE_LIST = List.of(
            "/login", "/refresh-token", "/reset-password", "/send-code"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> WHITE_LIST.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));


}
