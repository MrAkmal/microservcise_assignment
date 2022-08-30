package com.example.procurement_nature_service.procurementNature;

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
public class ProcurementNatureService {


    private final ProcurementNatureRepository repository;
    private final WebClient webClient;

    @Value("${procurement_method_base_url}")
    String procurementMethodBaseUrl;

    @Autowired
    public ProcurementNatureService(ProcurementNatureRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }


    public Mono<ProcurementNature> save(ProcurementNatureDTO procurementNature) {

        return repository.save(new ProcurementNature(procurementNature.getName()));
    }

    public Mono<ProcurementNature> update(ProcurementNatureDTO procurementNature) {

        return repository.findById(procurementNature.getId()).flatMap(procurementNature1 -> {
            procurementNature1.setName(procurementNature.getName());
            return repository.save(procurementNature1);
        }).switchIfEmpty(Mono.empty());
    }

    public Mono<Void> delete(Integer deletedId) {

        deleteProcurementMethod(deletedId).subscribe(f -> System.out.println("f= " + f));

        return repository.findById(deletedId)
                .flatMap(procurementNature -> repository.deleteById(deletedId))
                .switchIfEmpty(Mono.empty());

    }


    public Mono<ProcurementNature> get(int id) {
        return repository.findById(id).switchIfEmpty(Mono.empty());
    }

    public Flux<ProcurementNature> getAll() {

        Flux<ProcurementNature> all = repository.findAll();

        System.out.println("all = " + all);

        return all;
    }

    public Flux<ProcurementNature> getAll(String fieldName) {

        Flux<ProcurementNature> all = repository.findAll(Sort.by(Sort.Direction.ASC, fieldName));

        System.out.println("all = " + all);

        return all;
    }

    public Mono<Void> deleteProcurementMethod(Integer procurementNatureId) {
        System.out.println("procurementNatureId = " + procurementNatureId);

        return webClient.delete()
                .uri(procurementMethodBaseUrl + "/delete_by_procurement_nature_id/" + procurementNatureId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
