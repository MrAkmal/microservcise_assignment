package com.example.payment_microservice.payment;

import com.example.payment_microservice.paymenttype.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PaymentBaseService {


    private final PaymentBaseRepository repository;

    private final PaymentBaseMapper mapper;

    private final PaymentTypeService paymentTypeService;


    @Autowired
    public PaymentBaseService(PaymentBaseRepository repository, PaymentBaseMapper mapper, PaymentTypeService paymentTypeService) {
        this.repository = repository;
        this.mapper = mapper;
        this.paymentTypeService = paymentTypeService;
    }


    public Flux<PaymentBaseDTO> getAll() {
//        return repository.findAll().switchIfEmpty(Flux.empty()).map(mapper::toDTO);
        return repository.findAll().switchIfEmpty(Flux.empty())
                .flatMap(paymentBase -> {
            return paymentTypeService.get(paymentBase.getPaymentTypeId()).map(paymentType -> {
                return mapper.toDTO(paymentBase, paymentType.getType());
            });
        });
    }

    public Mono<PaymentBaseDTO> get(Integer id) {

        return repository.findById(id).switchIfEmpty(Mono.empty()).flatMap(paymentBase -> {
            return paymentTypeService.get(paymentBase.getPaymentTypeId()).map(paymentType -> {
                return mapper.toDTO(paymentBase, paymentType.getType());
            });
        });

    }

    public Mono<PaymentBaseDTO> save(PaymentBaseCreateDTO dto) {


        Mono<PaymentBase> paymentBaseMono = repository.save(mapper.fromCreateDTO(dto));

        return paymentBaseMono.flatMap(paymentBase -> {
            return paymentTypeService.get(paymentBase.getPaymentTypeId()).map(paymentType -> {
                return mapper.toDTO(paymentBase, paymentType.getType());
            });
        });
    }


    public Flux<PaymentBaseDTO> saveAll(List<PaymentBaseCreateDTO> dto) {

        Flux<PaymentBase> paymentBaseFlux = repository.saveAll(mapper.fromCreateDTO(dto));

        return paymentBaseFlux.flatMap(paymentBase -> {
            return paymentTypeService.get(paymentBase.getPaymentTypeId()).map(paymentType -> {
                return mapper.toDTO(paymentBase, paymentType.getType());
            });
        });
    }


    public Mono<PaymentBaseDTO> update(PaymentBaseUpdateDTO dto) {

        return repository.findById(dto.getId())
                .switchIfEmpty(Mono.empty())
                .flatMap(paymentBase -> repository.save(mapper.fromUpdateDTO(dto)).flatMap(paymentBase2 -> {
                    return paymentTypeService.get(paymentBase2.getPaymentTypeId()).map(paymentType -> {
                        return mapper.toDTO(paymentBase2, paymentType.getType());
                    });
                }));

    }

    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }
}
