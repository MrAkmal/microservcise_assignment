package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.ProcurementMethodDTO;
import com.example.payment_microservice.dto.ProcurementNatureDTO;
import com.example.payment_microservice.dto.TypeCreateDTO;
import com.example.payment_microservice.payment.*;
import com.example.payment_microservice.paymenttype.PaymentTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class PaymentConfigurationService {

    private final PaymentConfigurationRepository repository;
    private final PaymentBaseRepository paymentBaseRepository;
    private final PaymentBaseService paymentBaseService;

    private final PaymentConfigurationMapper mapper;


    private final String procurementMethodURI = "http://localhost:2020/v1/procurement_method";
    private final String procurementNatureURI = "http://localhost:1010/v1/procurement_nature";

    @Autowired
    public PaymentConfigurationService(PaymentConfigurationRepository repository, PaymentBaseRepository paymentBaseRepository, PaymentBaseService paymentBaseService, PaymentConfigurationMapper mapper, PaymentTypeService paymentTypeService, PaymentBaseService paymentBaseService1) {
        this.repository = repository;
        this.paymentBaseRepository = paymentBaseRepository;
        this.mapper = mapper;
        this.paymentBaseService = paymentBaseService1;
    }


    public Flux<PaymentConfigurationDTO> getAll() {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String header = request.getHeader(AUTHORIZATION);


        Flux<PaymentConfiguration> paymentConfigurationFlux = repository.findAll();

        return paymentConfigurationFlux.flatMap(paymentConfiguration -> get(paymentConfiguration.getId(), header));

    }


    public Mono<PaymentConfigurationDTO> get(Integer id, String header) {

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
                    .header(AUTHORIZATION, header)
                    .retrieve()
                    .bodyToMono(ProcurementMethodDTO.class);

            Mono<ProcurementNatureDTO> procurementNatureMono = WebClient.builder().build()
                    .get()
                    .uri(procurementNatureURI + "/{id}", paymentConfiguration.getProcurementNatureId())
                    .header(AUTHORIZATION, header)
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
                            .procurementMethodName(objects.getT1().getWiseName())
                            .procurementNatureName(objects.getT2().getName())
                            .build();

                });
            });

        });

        return dtoMono;
    }


    @Transactional
    public Mono<PaymentConfigurationDTO> save(PaymentConfigurationCreateDTO dto, String authorizationHeader) {


        Mono<ProcurementMethodDTO> procurementMethodMono = WebClient.builder().build()
                .get()
                .uri(procurementMethodURI + "/{id}", dto.getProcurementMethodId())
                .header(AUTHORIZATION, authorizationHeader)
                .retrieve()
                .bodyToMono(ProcurementMethodDTO.class);

        Mono<ProcurementNatureDTO> procurementNatureMono = WebClient.builder().build()
                .get()
                .uri(procurementNatureURI + "/{id}", dto.getProcurementNatureId())
                .header(AUTHORIZATION, authorizationHeader)
                .retrieve()
                .bodyToMono(ProcurementNatureDTO.class);


        Mono<PaymentConfiguration> paymentConfigurationMono = procurementMethodMono
                .flatMap(procurementMethodDTO -> {

            return procurementNatureMono.flatMap(procurementNatureDTO -> {

                Mono<PaymentConfiguration> paymentConfigurationMono1 = repository.save(mapper.fromCreateDTO(dto));
                return paymentConfigurationMono1;
            });
        });


        return paymentConfigurationMono.flatMap(paymentConfiguration -> get(paymentConfiguration.getId(), authorizationHeader));

    }


    @Transactional
    public Mono<PaymentConfiguration> update(PaymentConfigurationCreateDTO dto, String header) {

        List<PaymentBaseCreateDTO> types = dto.getTypes();
        int id = dto.getId();

        Mono<Void> delete = deletePaymentTypes(id);
        delete.subscribe(System.out::println);

        Flux<PaymentBaseDTO> paymentBaseDTOFlux = paymentBaseService.saveAll(types);
        paymentBaseDTOFlux.subscribe(System.out::println);


        Mono<ProcurementMethodDTO> procurementMethodMono = WebClient.builder().build()
                .get()
                .uri(procurementMethodURI + "/{id}", dto.getProcurementMethodId())
                .header(AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(ProcurementMethodDTO.class);

        Mono<ProcurementNatureDTO> procurementNatureMono = WebClient.builder().build()
                .get()
                .uri(procurementNatureURI + "/{id}", dto.getProcurementNatureId())
                .header(AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(ProcurementNatureDTO.class);

        return procurementMethodMono.flatMap(
                procurementMethodDTO -> procurementNatureMono.flatMap(procurementNatureDTO -> {
                    return repository.findById(id)
                            .flatMap(paymentConfiguration -> {
                                paymentConfiguration.setProcurementMethodId(dto.getProcurementMethodId());
                                paymentConfiguration.setProcurementNatureId(dto.getProcurementNatureId());
                                return repository.save(paymentConfiguration);
                            }).switchIfEmpty(Mono.empty());
                })
        );

    }

    private Mono<Void> deletePaymentTypes(int id) {
        return paymentBaseRepository.deleteByPaymentConfigId(id);
    }


    public Mono<Void> delete(Integer id) {

        Mono<Void> mono = paymentBaseRepository.deleteByPaymentConfigId(id);
        mono.subscribe(System.out::println);
        return repository.deleteById(id);
    }

    public Mono<PaymentConfiguration> findById(Integer id) {
        return repository.findById(id).switchIfEmpty(Mono.empty());
    }
}
