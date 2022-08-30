package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.ProcurementMethodDTO;
import com.example.payment_microservice.dto.ProcurementNatureDTO;
import com.example.payment_microservice.payment.*;
import com.example.payment_microservice.paymenttype.PaymentTypeCreateDTO;
import com.example.payment_microservice.paymenttype.PaymentTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
public class PaymentConfigurationService {

    private final PaymentConfigurationRepository repository;
    private final PaymentBaseRepository paymentBaseRepository;

    private final PaymentConfigurationMapper mapper;


    private final String procurementMethodURI = "http://localhost:2020/v1/procurement_method";
    private final String procurementNatureURI = "http://localhost:1010/v1/procurement_nature";

    @Autowired
    public PaymentConfigurationService(PaymentConfigurationRepository repository, PaymentBaseRepository paymentBaseRepository, PaymentBaseService paymentBaseService, PaymentConfigurationMapper mapper, PaymentTypeService paymentTypeService) {
        this.repository = repository;
        this.paymentBaseRepository = paymentBaseRepository;
        this.mapper = mapper;
    }


    public Flux<PaymentConfigurationDTO> getAll() {

        Flux<PaymentConfiguration> paymentConfigurationFlux = repository.findAll();

        return paymentConfigurationFlux.flatMap(paymentConfiguration -> get(paymentConfiguration.getId()));

    }


    public Mono<PaymentConfigurationDTO> get(Integer id) {

        Mono<PaymentConfiguration> paymentConfigurationMono = repository.findById(id);

        Mono<PaymentConfigurationDTO> dtoMono = paymentConfigurationMono.flatMap(paymentConfiguration -> {


            Flux<String> paymentBaseFlux = paymentBaseRepository.findPaymentBasesByPaymentConfigurationId(paymentConfiguration.getId());

            Flux<PaymentBaseTypeDTO> paymentBaseTypeDTOFlux = paymentBaseFlux.map(s -> {

                try {
                    return new ObjectMapper().readValue(s, new TypeReference<>() {
                    });

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });


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


            return paymentBaseTypeDTOFlux.collectList().flatMap(paymentBase -> {

                return zip.map(objects -> {

                    return PaymentConfigurationDTO.builder()
                            .id(paymentConfiguration.getId())
                            .payments(paymentBase.stream().map(paymentBase1 -> {
                                return PaymentBaseDTO.builder()
                                        .id(paymentBase1.getId())
                                        .paymentType(paymentBase1.getType())
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

        Mono<Void> mono = paymentBaseRepository.deleteByPaymentConfigId(id);
        mono.subscribe(a -> System.out.println(a));
        return repository.deleteById(id);
    }
}
