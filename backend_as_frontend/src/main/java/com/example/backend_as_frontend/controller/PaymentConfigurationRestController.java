package com.example.backend_as_frontend.controller;


import com.example.backend_as_frontend.dto.PaymentConfigCreateDTO;
import com.example.backend_as_frontend.dto.PaymentConfigurationDTO;
import com.example.backend_as_frontend.entity.PaymentType;
import com.example.backend_as_frontend.service.PaymentConfigurationService;
import com.example.backend_as_frontend.service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/payment_config_rest")
public class PaymentConfigurationRestController {

    private final PaymentConfigurationService paymentConfigurationService;
    private final PaymentTypeService paymentTypeService;


    @Autowired
    public PaymentConfigurationRestController(PaymentConfigurationService paymentConfigurationService, PaymentTypeService paymentTypeService) {
        this.paymentConfigurationService = paymentConfigurationService;
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping
    public List<PaymentType> getAllTypes() {
        List<PaymentType> all = paymentTypeService.getAll();
        return all;
    }

    @PostMapping
    public ResponseEntity<Void> savePaymentConfig(@RequestBody PaymentConfigCreateDTO paymentConfig) {
        System.out.println("dto = " + paymentConfig);
        paymentConfigurationService.save(paymentConfig);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public List<PaymentConfigurationDTO> getAllPaymentConfig() {
        return paymentConfigurationService.getAll();
    }
}

