package com.example.payment_microservice.paymentconfiguration;


import com.example.payment_microservice.dto.ProcurementMethodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/payment_configuration")
public class PaymentConfigurationController {

    private final PaymentConfigurationService service;


    @Autowired
    public PaymentConfigurationController(PaymentConfigurationService service) {
        this.service = service;
    }


    @GetMapping
    public Flux<PaymentConfigurationDTO> getAll() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    public Mono<PaymentConfigurationDTO> get(@PathVariable("id") Integer id) {
        return service.get(id);
    }


    @PostMapping
    public Mono<PaymentConfigurationDTO> save(@RequestBody PaymentConfigurationCreateDTO dto) {
        return service.save(dto);

    }


    @GetMapping("/test")
    public String test() {
        Mono<ProcurementMethodDTO> procurementMethodMono = WebClient.builder().build()
                .get()
                .uri("http://localhost:2020/v1/procurement_method/{id}",10)
                .retrieve()
                .bodyToMono(ProcurementMethodDTO.class);

        procurementMethodMono.subscribe(procurementMethodDTO -> {
            System.out.println("procurementMethodDTO.getName() = " + procurementMethodDTO.getName());
        });

        return "ishladi";
    }

    @PutMapping
    public Mono<Void> update(@RequestBody PaymentConfigurationUpdateDTO dto) {
        return service.update(dto);
    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Integer id) {
        return service.delete(id);
    }


}
