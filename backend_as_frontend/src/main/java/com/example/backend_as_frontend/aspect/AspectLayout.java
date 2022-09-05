package com.example.backend_as_frontend.aspect;


import com.example.backend_as_frontend.service.KeywordBaseService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Arrays;

@Component
@Aspect
public class AspectLayout {

    private final KeywordBaseService service;

    public AspectLayout(KeywordBaseService service) {
        this.service = service;
    }

    @Before("execution(* com.example.backend_as_frontend.controller.*.*(..))")
    public void forAllController(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        String wiseName = service.getWiseName();

        Arrays.stream(args).filter(arg -> arg instanceof Model).forEach(arg -> ((Model) arg).addAttribute("wiseName", wiseName));

    }


}
