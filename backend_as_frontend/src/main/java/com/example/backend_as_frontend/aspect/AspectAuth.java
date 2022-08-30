package com.example.backend_as_frontend.aspect;


import com.example.backend_as_frontend.handler.CustomException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class AspectAuth {


    @Pointcut("execution(* com.example.backend_as_frontend.controller.FinancialYearController.*(..))")
    public void forFinancialController() {
    }

    @Pointcut("execution(* com.example.backend_as_frontend.controller.HomeController.*(..))")
    public void forHomeController() {
    }


    @Pointcut("execution(* com.example.backend_as_frontend.controller.ProcurementMethodController.*(..))")
    public void forProcurementMethod() {
    }

    @Pointcut("execution(* com.example.backend_as_frontend.controller.ProcurementNatureController.*(..))")
    public void forProcurementNature() {
    }

    @Before("forHomeController() || forFinancialController() || forProcurementMethod() || forProcurementNature())")
    public String check() {



        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Cookie[] cookies = request.getCookies();

//        if (Objects.isNull(cookies)) return "redirect:/login";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ACCESSTOKEN") && cookie.getValue().length()>0) {
                    System.out.println("cookie.getValue() = " + cookie.getValue());
                    System.out.println("request.getRequestURI() = " + request.getRequestURI());
                    return request.getRequestURI();
                }

            }
        }

        throw new CustomException("Login");
    }

}
