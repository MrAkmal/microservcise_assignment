package com.example.payment_microservice.payment;


import com.example.payment_microservice.paymenttype.PaymentBaseTypeProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/payment/payment_base")
public class PaymentBaseController {


    private final PaymentBaseService service;

    @Autowired
    public PaymentBaseController(PaymentBaseService service) {
        this.service = service;
    }




    @GetMapping
    public Flux<PaymentBaseDTO> getAll() {

        return service.getAll();
    }


    @GetMapping("/{id}")
    public Mono<PaymentBaseDTO> get(@PathVariable("id") Integer id) {
        return service.get(id);
    }


    @PostMapping
    public Mono<PaymentBaseDTO> save(@RequestBody PaymentBaseCreateDTO dto) {
        return service.save(dto);

    }


    @PostMapping("/save-all")
    public Flux<PaymentBaseDTO> saveAll(@RequestBody List<PaymentBaseCreateDTO> dto) {
        System.out.println("dto = " + dto);
        return service.saveAll(dto);
    }

    @PutMapping
    public Mono<PaymentBaseDTO> update(@RequestBody PaymentBaseUpdateDTO dto) {
        return service.update(dto);
    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Integer id) {
        return service.delete(id);
    }


    @GetMapping("/test")
    public Flux<String> test() {
        return service.test();
    }

}
