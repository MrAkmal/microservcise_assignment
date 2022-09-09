package com.example.payment_microservice.paymenttype;

import com.example.payment_microservice.payment.PaymentBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PaymentTypeService {


    private final PaymentTypeRepository repository;


    private final PaymentBaseRepository paymentBaseRepository;

    @Autowired
    public PaymentTypeService(PaymentTypeRepository repository, PaymentBaseRepository paymentBaseRepository) {
        this.repository = repository;
        this.paymentBaseRepository = paymentBaseRepository;
    }


    public Flux<PaymentType> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).switchIfEmpty(Flux.empty());
    }

    public Mono<PaymentType> get(Integer id) {
        return repository.findById(id).switchIfEmpty(Mono.empty());
    }

    public Mono<PaymentType> save(PaymentTypeCreateDTO dto) {


        return repository.save(new PaymentType(dto.getType())).doOnNext(paymentType -> {
            Mono<Void> voidMono = paymentBaseRepository.saveForPaymentType(paymentType.getId());
            voidMono.subscribe(System.out::println);
        }).onErrorReturn(new PaymentType());

    }

    public Flux<PaymentType> saveAll(List<PaymentTypeCreateDTO> types) {

        List<PaymentType> paymentTypes = types.stream().map(paymentTypeCreateDTO -> new PaymentType(paymentTypeCreateDTO.getType())).toList();

        return repository.saveAll(paymentTypes).onErrorReturn(new PaymentType());
    }

    public Mono<PaymentType> update(PaymentType dto) {
        return repository.findById(dto.getId())
                .flatMap(paymentType -> {
                    paymentType.setType(dto.getType());
                    return repository.save(paymentType);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Void> delete(Integer id) {

        Mono<Void> voidMono = paymentBaseRepository.deleteByPaymentTypeId(id);
        voidMono.subscribe(System.out::println);

        return repository.deleteById(id);
    }
}
