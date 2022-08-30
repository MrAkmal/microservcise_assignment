package com.example.payment_microservice.paymenttype;

import com.example.payment_microservice.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentTypeService {


    private final PaymentTypeRepository repository;

    @Autowired
    public PaymentTypeService(PaymentTypeRepository repository) {
        this.repository = repository;
    }


    public Flux<PaymentType> getAll() {
        return repository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<PaymentType> get(Integer id) {
        return repository.findById(id).switchIfEmpty(Mono.empty());
    }

    public Mono<PaymentType> save(PaymentTypeCreateDTO dto) {

        return repository.save(new PaymentType(dto.getType())).onErrorReturn(new PaymentType());
    }

    public Flux<PaymentType> saveAll(List<PaymentTypeCreateDTO> types) {

        List<PaymentType> paymentTypes = types.stream().map(paymentTypeCreateDTO -> new PaymentType(paymentTypeCreateDTO.getType())).toList();

        return repository.saveAll(paymentTypes).onErrorReturn(new PaymentType());
    }

    public Mono<PaymentType> update(PaymentType dto) {
        return repository.findById(dto.getId()).switchIfEmpty(Mono.empty()).flatMap(repository::save);
    }

    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }
}
