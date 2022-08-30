package com.example.payment_microservice.paymenttype;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/payment/payment_type")
public class PaymentTypeController {


    private final PaymentTypeService service;


    public PaymentTypeController(PaymentTypeService service) {
        this.service = service;
    }


    @GetMapping
    public Flux<PaymentType> getAll() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    public Mono<PaymentType> get(@PathVariable("id") Integer id) {
        return service.get(id);
    }


    @PostMapping
    public Mono<PaymentType> save(@RequestBody PaymentTypeCreateDTO dto) {
        return service.save(dto);

    }


    @PostMapping("/save-all")
    public Flux<PaymentType> saveAll(@RequestBody List<PaymentTypeCreateDTO> types) {
        return service.saveAll(types);
    }

    @PutMapping
    public Mono<PaymentType> update(@RequestBody PaymentType dto) {
        return service.update(dto);
    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Integer id) {
        return service.delete(id);
    }


}
