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


    public void saveAll(List<PaymentBaseCreateDTO> dto) {


//        Flux<PaymentBase> paymentBaseFlux = repository.saveAll(mapper.fromCreateDTO(dto));

        for (PaymentBaseCreateDTO paymentBaseCreateDTO : dto) {
            Mono<PaymentBaseDTO> save = save(paymentBaseCreateDTO);
        }

//        return paymentBaseFlux.map(mapper::toDTO);
    }


    public Mono<Void> update(PaymentBaseUpdateDTO dto) {

        repository.findById(dto.getId()).switchIfEmpty(Mono.empty()).map(paymentBase -> repository.save(mapper.fromUpdateDTO(dto)));

        return Mono.empty();
    }

    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }
}
