package com.example.payment_microservice.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Aspect
@Component
public class PaymentAspect {


    @Pointcut("execution( * com.example.payment_microservice.payment.PaymentBaseController.*(..))")
    public void forPayment() {
    }


    @Pointcut("execution( * com.example.payment_microservice.paymentconfiguration.PaymentConfigurationController.*(..))")
    public void forPaymentConfiguration() {
    }

    @Pointcut("execution( * com.example.payment_microservice.paymenttype.PaymentTypeController.*(..))")
    public void forPaymentType() {
    }


    @Before("forPayment() || forPaymentConfiguration() || forPaymentType()")
    public void beforeAll() {

        RestTemplate restTemplate = new RestTemplate();

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String token = authorizationHeader.substring(7);
        System.out.println("token = " + token);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(token, headers);

        boolean check = Boolean.TRUE.equals(restTemplate.exchange("http://localhost:7070/v1/authorization_server/authorize",
                HttpMethod.POST, entity, Boolean.class).getBody());

        if (!check) throw new RuntimeException("Token not valid");

    }

}
