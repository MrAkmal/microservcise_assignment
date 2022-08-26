package com.example.payment_microservice.paymentconfiguration;

import com.example.payment_microservice.dto.ProcurementMethodDTO;
import com.example.payment_microservice.dto.ProcurementNatureDTO;
import com.example.payment_microservice.payment.PaymentBase;
import com.example.payment_microservice.payment.PaymentBaseDTO;
import com.example.payment_microservice.payment.PaymentBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PaymentConfigurationService {

    private final PaymentConfigurationRepository repository;
    private final PaymentBaseRepository paymentBaseRepository;

    private final PaymentConfigurationMapper mapper;

    @Autowired
    public PaymentConfigurationService(PaymentConfigurationRepository repository, PaymentBaseRepository paymentBaseRepository, PaymentConfigurationMapper mapper) {
        this.repository = repository;
        this.paymentBaseRepository = paymentBaseRepository;
        this.mapper = mapper;
    }


    public Flux<PaymentConfigurationDTO> getAll() {

        Flux<PaymentConfiguration> paymentConfigurationFlux = repository.findAll();

        Flux<PaymentConfigurationDTO> dtoFlux = paymentConfigurationFlux.flatMap(paymentConfiguration -> {

            Mono<List<PaymentBase>> paymentBaseFlux = paymentBaseRepository.findAllByPaymentConfigurationId(paymentConfiguration.getId());

            Mono<ProcurementMethodDTO> procurementMethodMono = WebClient.builder().build()
                    .get()
                    .uri("")
                    .retrieve()
                    .bodyToMono(ProcurementMethodDTO.class);

            Mono<ProcurementNatureDTO> procurementNatureMono = WebClient.builder().build()
                    .get()
                    .uri("")
                    .retrieve()
                    .bodyToMono(ProcurementNatureDTO.class);


            Mono<Tuple2<ProcurementMethodDTO, ProcurementNatureDTO>> zip = Mono.zip(procurementMethodMono, procurementNatureMono);


            return paymentBaseFlux.flatMap(paymentBase -> {

                return zip.map(objects -> {
                    return PaymentConfigurationDTO.builder()
                            .id(paymentConfiguration.getId())
                            .payments(toBaseDTO(paymentBase))
                            .procurementMethodName(objects.getT1().getName())
                            .procurementNatureName(objects.getT2().getName())
                            .build();

                });
            });
        });

        return dtoFlux;
    }

    private List<PaymentBaseDTO> toBaseDTO(List<PaymentBase> paymentBase) {


        return paymentBase.stream().map(this::toBaseDTO).toList();

    }


    private PaymentBaseDTO toBaseDTO(PaymentBase paymentBase) {


        return PaymentBaseDTO.builder()
                .id(paymentBase.getId())
                .type(paymentBase.getType())
                .active(paymentBase.isActive())
                .build();


    }
}
