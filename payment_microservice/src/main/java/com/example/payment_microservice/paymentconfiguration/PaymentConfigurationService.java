package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.ProcurementMethodDTO;
import com.example.payment_microservice.dto.ProcurementNatureDTO;
import com.example.payment_microservice.payment.PaymentBase;
import com.example.payment_microservice.payment.PaymentBaseDTO;
import com.example.payment_microservice.payment.PaymentBaseRepository;
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
    public PaymentConfigurationService(PaymentConfigurationRepository repository, PaymentBaseRepository paymentBaseRepository, PaymentConfigurationMapper mapper) {
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

            Flux<PaymentBase> paymentBaseFlux = paymentBaseRepository.findPaymentBasesByPaymentConfigurationId(paymentConfiguration.getId());

            Mono<List<PaymentBase>> listMono = paymentBaseFlux.collectList();

            List<PaymentBaseDTO> paymentBaseDTOList = mapper.toDTO2(paymentBaseFlux);


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

            System.out.println("1");


            return zip.map(objects -> {
                System.out.println("2");

                return PaymentConfigurationDTO.builder()
                        .id(paymentConfiguration.getId())
                        .payments(paymentBaseDTOList)
                        .procurementMethodName(objects.getT1().getName())
                        .procurementNatureName(objects.getT2().getName())
                        .build();

            });

        });

        return dtoMono;
    }


    @Transactional
    public Mono<PaymentConfigurationDTO> save(PaymentConfigurationCreateDTO dto) {

        System.out.println("dto.getProcurementNatureId() = " + dto.getProcurementNatureId());
        System.out.println("dto.getProcurementMethodId() = " + dto.getProcurementMethodId());
        Mono<PaymentConfiguration> paymentConfigurationMono = repository.save(mapper.fromCreateDTO(dto));

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
