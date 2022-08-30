package com.example.procurement_nature_service.procurementNature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class ProcurementNatureService {


    private final ProcurementNatureRepository repository;


    @Autowired
    public ProcurementNatureService(ProcurementNatureRepository repository) {
        this.repository = repository;
    }


    public Mono<ProcurementNature> save(ProcurementNature procurementNature) {

        return repository.findProcurementNatureByName(procurementNature.getName())
                .flatMap(Mono::just)
                .switchIfEmpty(repository.save(procurementNature));
    }

    public Mono<ProcurementNature> update(ProcurementNature procurementNature) {

        return repository.findById(procurementNature.getId())
                .flatMap(procurementNature1 -> {
                    if (Objects.equals(procurementNature1.getName(), procurementNature.getName())) {
                        return repository.save(procurementNature);
                    } else {
                        return repository.findProcurementNatureIdAndNameNot(procurementNature.getName(), procurementNature.getId())
                                .flatMap(Mono::just)
                                .switchIfEmpty(repository.save(procurementNature));
                    }
                })
                .switchIfEmpty(Mono.just(procurementNature));
    }

    public Mono<Void> delete(Integer deletedId) {
        Mono<Void> voidMono = repository.deleteById(deletedId);
        return voidMono;
    }

    public Mono<ProcurementNature> get(int id) {

        Mono<ProcurementNature> mono = repository.findById(id);

        return mono;
    }

    public Flux<ProcurementNature> getAll() {

        Flux<ProcurementNature> all = repository.findAll();

        System.out.println("all = " + all);

        return all;
    }

    public Flux<ProcurementNature> getAll(String fieldName) {

        Flux<ProcurementNature> all = repository.findAll(Sort.by(Sort.Direction.ASC,fieldName));

        System.out.println("all = " + all);

        return all;
    }
}
