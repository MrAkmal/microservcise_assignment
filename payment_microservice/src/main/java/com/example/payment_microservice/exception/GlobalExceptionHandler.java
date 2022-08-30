package com.example.payment_microservice.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public Mono<String> handleAll(CustomException e) {
        System.out.println("e.getMessage() = " + e.getMessage());

        return Mono.just(e.getMessage());
    }


}
