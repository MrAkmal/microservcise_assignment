package com.example.procurement_method_service.procurementMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProcurementMethodService {


    private final ProcurementMethodRepository repository;
    private final WebClient webClient;

    @Value("${procurement_nature_base_url}")
    String procurementNatureBaseUrl;


    @Autowired
    public ProcurementMethodService(ProcurementMethodRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }


    public Mono<ProcurementMethod> save(ProcurementMethodDTO procurementMethod) {

        int procurementNatureId = procurementMethod.getProcurementNatureId();


        return getProcurementNature(procurementNatureId).flatMap(procurementNatureDTO ->
                        repository.save(new ProcurementMethod(procurementMethod.getName(), procurementNatureDTO.getId())))
                .switchIfEmpty(Mono.empty());

    }


    public Mono<ProcurementMethod> update(ProcurementMethodDTO method) {

        int procurementNatureId = method.getProcurementNatureId();

        Mono<ProcurementNatureDTO> procurementNature = getProcurementNature(procurementNatureId);

        return repository.findById(method.getId())
                .flatMap(procurementMethod -> procurementNature.flatMap(
                        procurementNatureDTO -> {
                            procurementMethod.setName(procurementMethod.getName());
                            procurementMethod.setProcurementNatureId(procurementNatureDTO.getId());
                            return repository.save(procurementMethod);
                        }
                ).switchIfEmpty(Mono.empty()))
                .switchIfEmpty(Mono.empty());

    }


    public Mono<Void> delete(Integer id) {

        return repository.deleteById(id);

    }


    public Mono<ProcurementMethod> get(Integer id) {

        return repository.findById(id)
                .switchIfEmpty(Mono.empty());

    }

    public Flux<ProcurementMethod> getAll() {

        Flux<ProcurementMethod> list = repository.findAll();

        System.out.println("list = " + list);

        return list;

    }


    public Flux<ProcurementMethodResponse> getAllSort(String fieldName) {

        Flux<ProcurementMethod> list = repository.findAll(Sort.by(Sort.Direction.ASC, fieldName));

        Flux<ProcurementMethodResponse> responseFlux =
                list.flatMap(procurementMethod ->
                        getProcurementNature(procurementMethod.getProcurementNatureId())
                        .flatMap(procurementNatureDTO -> {
                            String procurementNatureName = procurementNatureDTO.getName();
                            return Mono.just(new ProcurementMethodResponse(
                                    procurementMethod.getId(),
                                    procurementMethod.getName(),
                                    procurementNatureName
                            ));
                        }).switchIfEmpty(Mono.just(new ProcurementMethodResponse(
                                procurementMethod.getId(),
                                procurementMethod.getName(),
                                "NOT FOUND"
                        )))).switchIfEmpty(Mono.empty());

        System.out.println("list = " + responseFlux);

        return responseFlux;

    }


    public Mono<ProcurementNatureDTO> getProcurementNature(int id) {
        return webClient.get()
                .uri(procurementNatureBaseUrl + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ProcurementNatureDTO.class);
    }

    public Mono<Void> deleteProcurementMethodByProcurementNatureId(Integer procurementNatureId) {

        return repository.updateByProcurementNatureId(procurementNatureId);
//        return repository.deleteByProcurementNatureId(procurementNatureId);
    }

}
