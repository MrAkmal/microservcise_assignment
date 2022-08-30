package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.ProcurementMethodDTO;
import com.example.payment_microservice.dto.ProcurementNatureDTO;
import com.example.payment_microservice.payment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentConfigurationService {

    private final PaymentConfigurationRepository repository;
    private final PaymentBaseRepository paymentBaseRepository;

    private final PaymentBaseService paymentBaseService;
    private final PaymentConfigurationMapper mapper;


    private final String procurementMethodURI = "http://localhost:2020/v1/procurement_method";
    private final String procurementNatureURI = "http://localhost:1010/v1/procurement_nature";

    @Autowired
    public PaymentConfigurationService(PaymentConfigurationRepository repository, PaymentBaseRepository paymentBaseRepository, PaymentBaseService paymentBaseService, PaymentConfigurationMapper mapper) {
        this.repository = repository;
        this.paymentBaseRepository = paymentBaseRepository;
        this.paymentBaseService = paymentBaseService;
        this.mapper = mapper;
    }


    public Flux<PaymentConfigurationDTO> getAll() {

        Flux<PaymentConfiguration> paymentConfigurationFlux = repository.findAll();

        return paymentConfigurationFlux.flatMap(paymentConfiguration -> get(paymentConfiguration.getId()));

    }


    public Mono<PaymentConfigurationDTO> get(Integer id) {

        Mono<PaymentConfiguration> paymentConfigurationMono = repository.findById(id);

        Mono<PaymentConfigurationDTO> dtoMono = paymentConfigurationMono.flatMap(paymentConfiguration -> {


            Flux<PaymentBase> paymentBaseFlux = paymentBaseRepository.findPaymentBasesByPaymentConfigurationId(paymentConfiguration.getId());


            Mono<ProcurementMethodDTO> procurementMethodMono = WebClient.builder().build()
                    .get()
                    .uri(procurementMethodURI + "/{id}", paymentConfiguration.getProcurementMethodId())
                    .retrieve()
                    .bodyToMono(ProcurementMethodDTO.class);

            Mono<ProcurementNatureDTO> procurementNatureMono = WebClient.builder().build()
                    .get()
                    .uri(procurementNatureURI + "/{id}", paymentConfiguration.getProcurementNatureId())
                    .retrieve()
                    .bodyToMono(ProcurementNatureDTO.class);

            Mono<Tuple2<ProcurementMethodDTO, ProcurementNatureDTO>> zip = Mono.zip(procurementMethodMono, procurementNatureMono);

            return paymentBaseFlux.collectList().flatMap(paymentBase -> {


                return zip.map(objects -> {

                    return PaymentConfigurationDTO.builder()
                            .id(paymentConfiguration.getId())
                            .payments(paymentBase.stream().map(paymentBase1 -> {
                                return PaymentBaseDTO.builder()
                                        .id(paymentBase1.getId())
                                        .type(paymentBase1.getType())
                                        .active(paymentBase1.isActive())
                                        .build();
                            }).toList())
                            .procurementMethodName(objects.getT1().getName())
                            .procurementNatureName(objects.getT2().getName())
                            .build();

                });
            });

        });

        return dtoMono;
    }


    @Transactional
    public Mono<PaymentConfigurationDTO> save(PaymentConfigurationCreateDTO dto) {

        Mono<ProcurementMethodDTO> procurementMethodMono = WebClient.builder().build()
                .get()
                .uri(procurementMethodURI + "/{id}", dto.getProcurementMethodId())
                .retrieve()
                .bodyToMono(ProcurementMethodDTO.class);

        Mono<ProcurementNatureDTO> procurementNatureMono = WebClient.builder().build()
                .get()
                .uri(procurementNatureURI + "/{id}", dto.getProcurementNatureId())
                .retrieve()
                .bodyToMono(ProcurementNatureDTO.class);


        Mono<PaymentConfiguration> paymentConfigurationMono = procurementMethodMono.flatMap(procurementMethodDTO -> {

            return procurementNatureMono.flatMap(procurementNatureDTO -> {

                Mono<PaymentConfiguration> paymentConfigurationMono1 = repository.save(mapper.fromCreateDTO(dto));
//                        .doOnNext(paymentConfiguration -> {
//                    List<PaymentBaseCreateDTO> paymentBaseCreateDTOS = dto.getTypes().stream().map(typeCreateDTO -> {
//                        return PaymentBaseCreateDTO.builder()
//                                .type(typeCreateDTO.getType())
//                                .active(typeCreateDTO.isActive())
//                                .paymentConfigurationId(paymentConfiguration.getId())
//                                .build();
//                    }).toList();
//
//                    paymentBaseCreateDTOS.forEach(paymentBaseCreateDTO -> {
//                        System.out.println("paymentBaseCreateDTO.getPaymentConfigurationId() = " + paymentBaseCreateDTO.getPaymentConfigurationId());
//                        System.out.println("paymentBaseCreateDTO.getType() = " + paymentBaseCreateDTO.getType());
//                        System.out.println("paymentBaseCreateDTO.isActive() = " + paymentBaseCreateDTO.isActive());
//                    });
//
//                    Flux<PaymentBaseDTO> paymentBaseDTOFlux = paymentBaseService.saveAll(paymentBaseCreateDTOS);
//
//                    paymentBaseDTOFlux.subscribe(System.out::println);
//
//                });
                return paymentConfigurationMono1;
            });
        });


        return paymentConfigurationMono.flatMap(paymentConfiguration -> get(paymentConfiguration.getId()));

    }


    public Mono<Void> update(PaymentConfigurationUpdateDTO dto) {

        Mono<PaymentConfiguration> paymentConfigurationMono = repository.findById(dto.getId())
                .switchIfEmpty(Mono.empty())
                .flatMap(paymentConfiguration -> repository.save(mapper.fromUpdateDTO(dto)));

        return Mono.empty();


    }


    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }
}
