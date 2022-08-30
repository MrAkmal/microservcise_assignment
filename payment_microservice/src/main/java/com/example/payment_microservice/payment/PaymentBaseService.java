package com.example.payment_microservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PaymentBaseService {


    private final PaymentBaseRepository repository;

    private final PaymentBaseMapper mapper;

    @Autowired
    public PaymentBaseService(PaymentBaseRepository repository, PaymentBaseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public Flux<PaymentBaseDTO> getAll() {
        return repository.findAll().switchIfEmpty(Flux.empty()).map(mapper::toDTO);
    }

    public Mono<PaymentBaseDTO> get(Integer id) {

        return repository.findById(id).switchIfEmpty(Mono.empty()).map(mapper::toDTO);

    }

    public Mono<PaymentBaseDTO> save(PaymentBaseCreateDTO dto) {


        Mono<PaymentBase> paymentBaseMono = repository.save(mapper.fromCreateDTO(dto));

        return paymentBaseMono.map(mapper::toDTO);
    }


    public Flux<PaymentBaseDTO> saveAll(List<PaymentBaseCreateDTO> dto) {

        System.out.println("\nPayment Base Service\n");

        dto.forEach(paymentBaseCreateDTO -> {
            System.out.println("paymentBaseCreateDTO.getPaymentConfigurationId() = " + paymentBaseCreateDTO.getPaymentConfigurationId());
            System.out.println("paymentBaseCreateDTO.getType() = " + paymentBaseCreateDTO.getType());
            System.out.println("paymentBaseCreateDTO.isActive() = " + paymentBaseCreateDTO.isActive());
        });

        Flux<PaymentBase> paymentBaseFlux = repository.saveAll(mapper.fromCreateDTO(dto));

        return paymentBaseFlux.map(mapper::toDTO);
    }


    public Mono<PaymentBaseDTO> update(PaymentBaseUpdateDTO dto) {

        return repository.findById(dto.getId())
                .switchIfEmpty(Mono.empty())
                .flatMap(paymentBase -> repository.save(mapper.fromUpdateDTO(dto)).map(mapper::toDTO));

    }

    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }
}
